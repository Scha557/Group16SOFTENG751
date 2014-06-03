package pi.util;

/**
 * Put all flags in this class in order to reduce condition checks in frequently invoked method such as <code>hasNext()</code>.
 *
 * Based off of xiaoxing's ThreadID
 * Adjusted by: Anneke Smitheram
 * Date: 31/05/2014
 */

import pi.util.Flags.Flag;

public class FlagsForTask {
	public enum Flag {
		BREAK(1),
		RESET(1 << 1);

		private int value;
		private Flag(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	};

	final private TaskID taskId;

	final private TaskLocal<Integer> flags;

	public FlagsForTask(TaskID taskId) {
		this.taskId = taskId;
		flags = new TaskLocal<Integer>(taskId, 0);
	}

	public boolean flagged() {
		return (0 != flags.get());
	}

	public void set(Flag flag) {
		flags.set(flags.get() | flag.getValue());
	}

	public void unset(Flag flag) {
		flags.set(flags.get() & ~flag.getValue());
	}

	public void setAll(Flag flag) {
		for (int i = 0; i < taskId.getThreadNum(); i++) {
			flags.set(i, flags.get(i) | flag.getValue());
		}
	}

	public void unsetAll(Flag flag) {
		for (int i = 0; i < taskId.getThreadNum(); i++) {
			flags.set(i, flags.get(i) & ~flag.getValue());
		}
	}

	public void reset() {
		flags.set(0);
	}

	public void resetAll() {
		flags.setAll(0);
	}

	public boolean flaggedWith(Flag flag) {
//		if ((flags.get() & flag.getValue()) != 0) {
////			unset(flag);	// After checking, unset the flag.
//			return true;
//		}
//		return false;
		return ((flags.get() & flag.getValue()) != 0);
	}
}
