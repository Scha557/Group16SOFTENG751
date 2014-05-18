
import pi.ParIterator;

public class WorkerThread extends Thread {

	private ParIterator<String> pi = null;
	private int id = -1;

	public WorkerThread(int id, ParIterator<String> pi) {
		this.id = id;
		this.pi = pi;
	}

	public void run() {
		while (pi.hasNext()) {
			String element = pi.next();
			System.out.println("Thread " + id + " got element: " + element);

			// slow down the threads (to illustrate the scheduling)
			try {
				if (id == 0)
					Thread.sleep(50);
				
				if (id == 1)
					Thread.sleep(1000);
				
				if (id == 2)
					Thread.sleep(150);
				
				if (id == 3)
					Thread.sleep(200);
				
				if (id == 4)
					Thread.sleep(250);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Thread "+id+" has finished.");
	}
}

