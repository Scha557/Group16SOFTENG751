package pi;

import pi.exceptions.PIExceptionHelper;
import pi.exceptions.ParIteratorException;
import pi.util.Flags;
import pi.util.FlagsForTask;
import pi.util.TLocal;
import pi.util.TaskID;
import pi.util.ThreadID;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Based off of xiaoxing's code
 * Adjusted by: Anneke Smitheram
 * Date: 31/05/13
 */
public abstract class TaskAwareParIteratorAbstract<E> implements ParIterator<E> {

	protected int taskChunkSize;
	protected int threadChunkSize;
	final protected int numOfThreads;
	final protected int numOfTasks;

	protected CountDownLatch threadLatch = null;
	protected CountDownLatch taskLatch = null;

	protected boolean ignoreBarrier = false;

	final protected ThreadID threadID;
	final protected TaskID taskID;

	protected List<E> data;

	protected Collection<E> collection;

	final protected ConcurrentLinkedQueue<Iterator<E>> reclaimedElements = new ConcurrentLinkedQueue<Iterator<E>>();

	/*
	 * the following variables are used for the purpose of exception handling
	 */
	//-- stores all the registered exceptions that occurred during traversal
	protected ConcurrentLinkedQueue<ParIteratorException<E>> exceptions = new ConcurrentLinkedQueue<ParIteratorException<E>>();

	//-- used for breaks, each thread checks their position
	final protected Flags threadFlags;
	final protected FlagsForTask taskFlags;

	private ReentrantLock localBreakLock = new ReentrantLock();

	public TaskAwareParIteratorAbstract(Collection<E> collection, int threadChunkSize, int numOfThreads, int numOfTasks, boolean ignoreBarrier) {
		this(numOfThreads, numOfTasks, ignoreBarrier);
		this.threadChunkSize = threadChunkSize;
		data = null;
		this.collection = collection;

	}

	public TaskAwareParIteratorAbstract(int numOfThreads, int numOfTasks, boolean ignoreBarrier) {
		this.numOfThreads = numOfThreads;
		this.numOfTasks = numOfTasks;
		
		threadID = new ThreadID(numOfThreads);
		taskID = new TaskID(numOfTasks);

		threadFlags = new Flags(threadID);
		taskFlags = new FlagsForTask(taskID);
		
		threadLatch = new CountDownLatch(numOfThreads);
		taskLatch = new CountDownLatch(numOfTasks);
		this.ignoreBarrier = ignoreBarrier;
		
	}

	public Collection<E> returnStuff() {
		return this.collection;
	}
	@Override
	public void remove() {
		throw new UnsupportedOperationException("This Parallel Iterator currently does not support remove()");
	}

	@Override
	public void reset() {
		throw new UnsupportedOperationException("Currently not supported.");
	}

	//TODO: globalBreak with tasks
	@Override
	public void globalBreak() {
		localBreakLock.lock();
		for (int i = 0; i < numOfThreads; i++)
			threadFlags.flaggedWith(Flags.Flag.BREAK);
		localBreakLock.unlock();
	}

	//TODO:
	protected boolean allOtherThreadsHaveLocalBreaked(int tid) {
		boolean allBreaked = true;
		for (int i = 0; i < numOfThreads; i++) {
		}
		return allBreaked;
	}


	@Override
	public void register(Exception e) {
		register(e, null);
	}

	public void register(Exception e, E currentElements) {
		Thread regThread = Thread.currentThread();
		ParIteratorException<E> pie = PIExceptionHelper.createException(e, currentElements, regThread);
		exceptions.add(pie);
	}

	@Override
	public ParIteratorException<E>[] getAllExceptions() {
		return exceptions.toArray(new ParIteratorException[] {});
	}

	// for test
	public int getID() {
		// TODO the thread ID thing need to be redesigned because of the reset feature.
		// here is a temporary solution.
		return threadID.get() % numOfThreads;
	}

	// for test
	public int getTaskID() {
		return taskID.get() % numOfTasks;
	}
}
