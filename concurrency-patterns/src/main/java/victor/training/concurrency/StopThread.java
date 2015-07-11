package victor.training.concurrency;

import static victor.training.concurrency.ConcurrencyUtil.log;
import static victor.training.concurrency.ConcurrencyUtil.sleepSomeTime;

public class StopThread {

	static class MyTask implements Runnable {
		private boolean running = true;
		public void run() {
			try {
				while (running) {
					log("Still alive, waiting...");
					Thread.sleep(1000);
				}
				log("Gracefully stopped execution");
			} catch (InterruptedException e) {
				log("Interrupted. Exiting");
			}
		}
		public void setRunning(boolean running) {
			this.running = running;
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		MyTask myTask = new MyTask();
		
		Thread t = new Thread(myTask);
		t.start();
		
		sleepSomeTime(2000, 3000);
		log("Trying to stop the thread");

		// TODO gracefully stop the thread
		myTask.setRunning(false); // SOLUTION
		// TODO force the .wait() to interrupt
		t.interrupt(); // SOLUTION
		
		log("Waiting for thread to finish...");
		t.join();
	}
	
}
