package pi;

import pi.collect.Lists;
import pi.util.Flags;
import pi.util.TLocal;
import pi.util.TaskLocal;

import java.util.*;

/**
 * Based off of xiaoxing's code
 * Adjusted by: Anneke Smitheram
 * Date: 31/05/13
 */
public class TaskAwareStaticParIterator<E> extends TaskAwareParIteratorAbstract<E> {

	final protected List<E> data;
	final protected List<List<E>> chunks;
	final protected TLocal<List<List<E>>> localChunks;

	final protected TLocal<Iterator<List<E>>> localChunkIterator;
	final protected TLocal<Iterator<E>> localIterator;

	public TaskAwareStaticParIterator(Collection<E> collection, int chunkSize, int numOfThreads, int numOfTasks, boolean ignoreBarrier) {
		super(collection, chunkSize, numOfThreads, numOfTasks,ignoreBarrier);
		
		data = formatData(collection);
		
		//If chunksize hasn't been specified, model it off how many tasks there are
		if (numOfTasks > 0 && this.threadChunkSize <= 0) {
			this.taskChunkSize = (int) Math.ceil((double) collection.size() / numOfTasks);
			chunks = Lists.partition(data, this.taskChunkSize);
		} else {
			//If no tasks, then go off thread size			
			if (this.threadChunkSize <= 0) {
				this.threadChunkSize = (int) Math.ceil((double) collection.size() / numOfThreads);
			}
			chunks = Lists.partition(data, this.threadChunkSize);			
		}
		
		localChunks = new TLocal<List<List<E>>>(threadID);
		for (int i = 0; i < chunks.size(); i++) {
			int tid = i % numOfThreads;
			if (localChunks.get(tid) == null) {
				localChunks.set(tid, new ArrayList<List<E>>());
			}
			localChunks.get(tid).add(chunks.get(i));
		}

		localChunkIterator = new TLocal<Iterator<List<E>>>(threadID, null);
		localIterator = new TLocal<Iterator<E>>(threadID, null);
	}

	protected List<E> formatData(Collection<E> collection) {
		List<E> data;
		if (collection instanceof RandomAccess) {
			data = (List<E>) collection;
		} else {
			data = new ArrayList<E>(collection);
		}
		return data;
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

		if (localChunkIterator.get() == null) {
			localChunkIterator.set(localChunks.get().iterator());
		}

		if (localIterator.get() == null) {
			if (localChunkIterator.get().hasNext()) {
				localIterator.set(localChunkIterator.get().next().iterator());
			} else {
				return false;
			}
		}

		if (!localIterator.get().hasNext()) {
			if (!localChunkIterator.get().hasNext()) {
				if (reclaimedElements.isEmpty()) {
					return false;
				} else {
					localIterator.set(reclaimedElements.poll());
				}
			} else {
				localIterator.set(localChunkIterator.get().next().iterator());
			}
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
		while (localChunkIterator.get().hasNext()) {
			reclaimedElements.add(localChunkIterator.get().next().iterator());
		}
		return true;
	}

	@Override
	public void reset() {
		threadFlags.setAll(Flags.Flag.RESET);
		reclaimedElements.clear();
		localChunkIterator.setAll(null);
		localIterator.setAll(null);
		threadFlags.resetAll();
	}
}
