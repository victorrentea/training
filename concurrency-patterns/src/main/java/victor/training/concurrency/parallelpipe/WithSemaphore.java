package victor.training.concurrency.parallelpipe;

import static victor.training.concurrency.ConcurrencyUtil.log;
import static victor.training.concurrency.ConcurrencyUtil.sleepSomeTime;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/*
 * The goal is to run step1 'ahead' of step2. To have step1 process one chunk of data ahead of step2. 
 * Both Steps are IO-bound BUT they CAN use distinct DB transactions.
 * If they needed the same DB Tx, they MUST be in the same Thread!! because JDBC Connection is 'thread-confined'. 
 * You cannot share a DB Tx/Connection among multiple threads.
 */

class Stuff {

	
	private static class RunnableReleasingSemaphore implements Runnable {
		private Runnable whatToDo;
		private Semaphore sem;

		public RunnableReleasingSemaphore(Semaphore sem, Runnable whatToDo) {
			this.sem = sem;
			this.whatToDo = whatToDo;
		}

		public void run() {
			try {
				sleepSomeTime(1000, 1001);
				sem.release();
				whatToDo.run();
			} finally {
			}
		}
		
	}
	
	private static void step2(String chunk) {
		
		log("Start Processing " + chunk);
		sleepSomeTime(100, 1000);
		log("Done");
	}

	public static void main(String[] args) throws InterruptedException {
//		ExecutorService executor = Executors.newSingleThreadExecutor();
		ExecutorService executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, 
				new ArrayBlockingQueue<>(2));

		Semaphore sem = new Semaphore(2);

		for (int i = 0; i < 50; i++) {
			String chunk = "chunk " + i;
			log("Start Parsing: " + chunk);
			sleepSomeTime(10, 11);
			log("Finished Parsing:" + chunk);
			////
			sem.acquire(1);
			log("Allowed to push");
			executor.execute(new RunnableReleasingSemaphore(sem, () -> step2(chunk)));
			
			////
		}
		log("Am terminat de parsat");

		////
		executor.shutdown();
		executor.awaitTermination(10, TimeUnit.MINUTES);
		/////
		log("Am terminat si de updatat. Ma uit la Versiune, lock, autocertificare. ...bla");

	}
}
