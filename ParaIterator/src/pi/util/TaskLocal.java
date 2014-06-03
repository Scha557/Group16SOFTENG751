package pi.util;

import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * This class works similar to the standard ThreadLocal but with
 * global control. All variables owned by individual threads can
 * be accessed globally.
 *
 * Based off of xiaoxing's ThreadID
 * Adjusted by: Anneke Smitheram
 * Date: 31/05/2014
 */
public class TaskLocal<E> {
	private AtomicReferenceArray<E> values;
//	private Object[] values;
	private TaskID taskId;

	public TaskLocal(TaskID taskId) {
		this(taskId, null);
	}

	public TaskLocal(TaskID taskId, E initialValue) {
		this.taskId = taskId;
		values = new AtomicReferenceArray<E>(taskId.getThreadNum());
		for (int i = 0; i < values.length(); i++) {
			values.set(i, initialValue);
		}
	}

	public E get() {
		return get(taskId.get());
	}

	public void set(E value) {
		set(taskId.get(), value);
	}

	public E get(int id) {
		return values.get(id);
	}

	public void set(int id, E value) {
		values.set(id, value);
	}

	public void setAll(E value) {
		for (int i = 0; i < values.length(); i++) {
			values.set(i, value);
		}
	}
}
