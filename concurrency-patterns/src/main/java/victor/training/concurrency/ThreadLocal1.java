package victor.training.concurrency;

import static victor.training.concurrency.ConcurrencyUtil.log;

public class ThreadLocal1 {
	// TODO declare a static thread local variable
	static ThreadLocal<String> threadStorage = new ThreadLocal<>(); // SOLUTION
	
	static class MyThread extends Thread {
		static void m1() {
			// no knowledge about the hidden thread local
			m2();
		}
		
		static void m2() {
			// hocus-pocus, here is the data
			String threadLocalData = threadStorage.get(); // SOLUTION
			// String threadLocalData = null; // TODO get from thread local // INITIAL
			log("Get thread local: " + threadLocalData);
		}
		public void run() {
			String dataToSet = "Data of " + getName();
			// TODO set in thread local 
			threadStorage.set(dataToSet); // SOLUTION
			log("Set thread local: " + dataToSet);
			ConcurrencyUtil.sleep2(300);
			m1();
		}
	}
	
	public static void main(String[] args) {
		new MyThread().start();
		new MyThread().start();
		new MyThread().start();
	}
}
