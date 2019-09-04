package io.infinitestrike.core.threading;

import java.util.ArrayList;
import java.util.UUID;

import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;

public class ThreadManager {
	private static ArrayList<ThreadObject> threads = new ArrayList<ThreadObject>();

	public static void addNewThreadObject(ThreadObject o) {
		ThreadManager.threads.add(o);
	}

	public static void startThread(int index) {
		if (index >= 0 && index <= threads.size() - 1) {
			threads.get(index).start();
			LogBot.logData(ThreadManager.class, Status.INFO, "Thread '" + threads.get(index).threadName + "' Started.");
		} else {
			LogBot.logData(ThreadManager.class, Status.INFO,
					"Index '" + index + "' is not in acceptable bounds. Expected [0 -> " + (threads.size() - 1 + "]"));
		}
	}

	public static void stopThread(int index) {
		if (index >= 0 && index <= threads.size() - 1) {
			if(!threads.get(index).stop()) {
				LogBot.logData(ThreadManager.class, Status.ERROR, "Unable to stop thread.");
			}else {
				LogBot.logData(ThreadManager.class, Status.INFO, "Thread '" + threads.get(index).threadName + "' Stopped.");
			}
		} else {
			LogBot.logData(ThreadManager.class, Status.INFO,
					"Index '" + index + "' is not in acceptable bounds. Expected [0 -> " + (threads.size() - 1 + "]"));
		}
	}

	public static void stopAllThreads() {
		for (int i = 0; i < threads.size(); i++) {
			stopThread(i);
		}
	}

	public static void startAllThreads() {
		for (int i = 0; i < threads.size(); i++) {
			startThread(i);
		}
	}

	public static void wakeThread(int index) {
		if (index >= 0 && index <= threads.size() - 1) {
			threads.get(index).thread.notifyAll();
		} else {
			LogBot.logData(ThreadManager.class, Status.INFO,
					"Index '" + index + "' is not in acceptable bounds. Expected [0 -> " + (threads.size() - 1 + "]"));
		}
	}

	public static void sleepThread(int index) {
		if (index >= 0 && index <= threads.size() - 1) {
			try {
				threads.get(index).thread.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				LogBot.logDataVerbose(e, Status.ERROR, "Cant interupt the thread: " + e.getMessage());
			}
		} else {
			LogBot.logData(ThreadManager.class, Status.INFO,
					"Index '" + index + "' is not in acceptable bounds. Expected [0 -> " + (threads.size() - 1 + "]"));
		}
	}

	public static void wakeAllThreads() {
		for (int i = 0; i < threads.size(); i++) {
			wakeThread(i);
		}
	}

	public static void removeThreadObject(ThreadObject o) {
		int index = getThreadObjectIndex(o);
		if (index != -1) {
			LogBot.logData(ThreadManager.class, Status.INFO, "Thread no longer managed.");
			ThreadManager.threads.remove(index);
			return;
		}
		LogBot.logData(ThreadManager.class, Status.WARNING, "Unable to remove thread. Index not resolved.");
	}

	public static int getThreadObjectIndex(ThreadObject o) {
		for (int i = 0; i < threads.size(); i++) {
			ThreadObject indexObject = threads.get(i);
			if (indexObject.thread.equals(o.thread) && indexObject.threadName.equals(o.threadName)) {
				return i;
			}
		}
		return -1;
	}

	public static ThreadObject getThreadObject(String name, Runnable r, boolean autoAdd) {
		ThreadObject o = new ThreadObject(name, new Thread(r, name));
		if (autoAdd) {
			LogBot.logData(ThreadManager.class, Status.INFO, "auto-adding thread '" + name + "' to manager.");
			ThreadManager.addNewThreadObject(o);
		}
		return o;
	}

	public static ThreadObject getThreadObject(String name, Runnable r) {
		return ThreadManager.getThreadObject(name, r, false);
	}

	public static ThreadObject getThreadObject(Runnable r) {
		String name = ThreadManager.generateRandomThreadName();
		return ThreadManager.getThreadObject(name, r);
	}

	public static ThreadObject getThreadObjectFromIndex(int index) {
		if (index >= 0 && index <= threads.size() - 1) {
			return threads.get(index);
		} else {
			LogBot.logData(ThreadManager.class, Status.INFO,
					"Index '" + index + "' is not in acceptable bounds. Expected [0 -> " + (threads.size() - 1 + "]"));
		}
		return null;
	}

	public static String generateRandomThreadName() {
		return "Thread/" + UUID.randomUUID().toString();
	}

	public static class ThreadObject {
		public Thread thread = null;
		public String threadName = "";

		private ThreadObject(String name, Thread thread) {
			this.thread = thread;
			this.threadName = name;
		}

		public boolean stop() {
			try {
				this.thread.join(100);
				return true;
			} catch (Exception e) {
				LogBot.logDataVerbose(e, Status.ERROR,
						"Unable to stop Thread '" + this.threadName + "'. " + e.getMessage());
			}
			return false;
		}

		public void start() {
			LogBot.logData(this.getClass(), Status.INFO, "Starting new Thread. '" + this.threadName + "'");
			this.thread.start();
		}

		public static ThreadObject createNewThreadObject(String name, Thread thread) {
			return new ThreadObject(name, thread);
		}

		public static ThreadObject createNewThreadObject(Thread thread) {
			String randomStringName = ThreadManager.generateRandomThreadName();
			return new ThreadObject(randomStringName, thread);
		}
	}
}
