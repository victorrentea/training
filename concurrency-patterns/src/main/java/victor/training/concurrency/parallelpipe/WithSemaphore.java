package victor.training.concurrency.parallelpipe;

import static victor.training.concurrency.ConcurrencyUtil.log;
import static victor.training.concurrency.ConcurrencyUtil.sleepSomeTime;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

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
				whatToDo.run();
			} finally {
				sem.release();
			}
		}
		
	}
	
	private static void step2(String chunk) {
		log("Start Processing " + chunk);
		sleepSomeTime(100, 1000);
		log("Done");
	}

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newSingleThreadExecutor();

		Semaphore sem = new Semaphore(1);

		for (int i = 0; i < 50; i++) {
			log("Start Parsing");
			sleepSomeTime(10, 100);
			String chunk = "chunk " + i;
			log("Gathered 500:" + chunk);
			////
			sem.acquire(1);
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
