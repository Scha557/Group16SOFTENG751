package pi;

import pi.collect.Iterables;
import pi.collect.Lists;
import pi.util.Flags;
import pi.util.TLocal;

import java.util.*;

/**
 * Based off of xiaoxing
 * Adjusted by: Anneke Smitheram
 * Date: 1/06/13
 */
public class TaskAwareDynamicParIterator<E> extends TaskAwareParIteratorAbstract<E> {

	private Iterator<List<E>> chunkIterator = null;

	final protected TLocal<Iterator<E>> localIterator = new TLocal<Iterator<E>>(threadID);

	public TaskAwareDynamicParIterator(final Collection<E> collection, final int chunkSize, final int numOfThreads, final int numOfTasks, final boolean ignoreBarrier) {
		super(collection, chunkSize, numOfThreads, numOfTasks, ignoreBarrier);
		if (this.threadChunkSize <= 0) {
			// the default chunkSize for Dynamic should be 1, which is consistent with the OpenMP specification.
			this.threadChunkSize = 1;
		}
		chunkIterator = partition(collection, this.threadChunkSize, numOfTasks);
	}

	protected Iterator<List<E>> partition(
			final Collection<E> collection, final int chunkSize, final int numOfTasks) {
		if (collection instanceof RandomAccess) {
			return new Iterator<List<E>>() {
				@Override
				public boolean hasNext() {
					return (cursor < data.size());
				}

				List<E> data = (List<E>)collection;
				int cursor = 0;

				@Override
				public synchronized List<E> next() {
					if (!hasNext()) {
						return null;
					}
					int from = cursor;
					int remaining = data.size() - from;
					int len = Math.min(remaining, chunkSize);
					cursor += len;
					return data.subList(from, from + len);
				}

				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};

		} else {
			final Iterator<E> iterator = collection.iterator();
			return new Iterator<List<E>>() {
				@Override
				public boolean hasNext() {
					return iterator.hasNext();
				}

				int remaining = collection.size();
				@Override
				public synchronized List<E> next() {
					if (!hasNext()) {
						return null;
					}
					int len = Math.min(remaining, chunkSize);
					Object[] array = new Object[len];
					// copy on demand
					int count = 0;
					for (; count < len && iterator.hasNext(); count++) {
						array[count] = iterator.next();
					}
					remaining -= len;

					@SuppressWarnings("unchecked")
					List<E> list = Collections.unmodifiableList(
							(List<E>) Arrays.asList(array));
					return list;
				}

				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}
	}

	@Override
	public boolean hasNext() {
		if (threadFlags.flagged()) {
			if (threadFlags.flaggedWith(Flags.Flag.BREAK)) {
				if (ignoreBarrier)
					return false;

				threadLatch.countDown();
				try {
					threadLatch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return false;
			}

			if (threadFlags.flaggedWith(Flags.Flag.RESET)) {
				return false;
			}
		}

		Iterator<E> iter = localIterator.get();
		if (iter == null || !iter.hasNext()) {
			List<E> chunk = chunkIterator.next();
			if (chunk == null) {
				if (reclaimedElements.isEmpty()) {
					return false;
				} else {
					iter = reclaimedElements.poll();
				}
			} else {
				iter = chunk.iterator();
			}
			localIterator.set(iter);
		}

		return localIterator.get().hasNext();
	}

	@Override
	public E next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		return localIterator.get().next();
	}

	@Override
	public boolean localBreak() {
		threadFlags.set(Flags.Flag.BREAK);
		reclaimedElements.add(localIterator.get());
		return true;
	}

	@Override
	public void reset() {
		threadFlags.setAll(Flags.Flag.RESET);
		reclaimedElements.clear();
		chunkIterator = partition(collection, threadChunkSize, numOfTasks);
		threadFlags.resetAll();
	}
}
