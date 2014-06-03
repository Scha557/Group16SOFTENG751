package pi.util;

import java.util.concurrent.atomic.AtomicInteger;

public class TaskID {
	final private AtomicInteger nextID = new AtomicInteger(0);
	final private ThreadLocal<Integer> taskID = new ThreadLocal<Integer>() {
		@Override
		protected Integer initialValue() {
			return nextID.getAndIncrement();
		}
	};

	final private int taskNum;

	public TaskID(int taskNum) {
		this.taskNum = taskNum;
	}

	public int getThreadNum() {
		return taskNum;
	}

	public int get() {
		return taskID.get() % taskNum;
	}

	// Static Task ID
	private static AtomicInteger nextStaticID = new AtomicInteger(0);

	private static ThreadLocal<Integer> staticID = new ThreadLocal<Integer>() {
		@Override
		protected Integer initialValue() {
			return nextStaticID.getAndIncrement();
		}
	};

	public static int getStaticID() {
		return staticID.get();
	}
}
