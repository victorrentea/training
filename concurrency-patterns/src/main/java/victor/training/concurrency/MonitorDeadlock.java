package victor.training.concurrency;

import static victor.training.concurrency.ConcurrencyUtil.log;


public class MonitorDeadlock {
	private static Object monitorA = new Object();
	private static Object monitorB = new Object();
	
	public static class Thread1 extends Thread {
		public void run() {
			try {
				synchronized (monitorA) {
					log("Acquired Monitor A");
					Thread.sleep(1);
					synchronized (monitorB) {
						log("Acquired Monitor B");
						Thread.sleep(1);
						log("Run");
					}
					log("Released Monitor B");
				}
				log("Released Monitor A");
			} catch (InterruptedException e) {}
		}
	}
	public static class Thread2 extends Thread {
		public void run() {
			try {
				// INITIAL(
				// // TODO Fix Deadlock
//				synchronized (monitorB) {
//					log("Acquired Monitor B");
//					Thread.sleep(1);
//					synchronized (monitorA) {
//						log("Acquired Monitor A");
//						Thread.sleep(1);
//						log("Run");
//					}
//					log("Released Monitor A");
//				}
//				log("Released Monitor B");
				// INITIAL)
				
				// SOLUTION(
				synchronized (monitorA) {
					log("Acquired Monitor A");
					Thread.sleep(1);
					synchronized (monitorB) {
						log("Acquired Monitor B");
						Thread.sleep(1);
						log("Run");
					}
					log("Released Monitor B");
				}
				log("Released Monitor A");
				// SOLUTION)
			} catch (InterruptedException e) {}
		}
	}
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread1();
		Thread t2 = new Thread2();
		
		t1.start();
		t2.start();
		log("Started");
		t1.join();
		t2.join();
		log("Finished");
	}
}
