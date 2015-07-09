package victor.training.concurrency;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static victor.training.concurrency.ConcurrencyUtil.log;
import static victor.training.concurrency.ConcurrencyUtil.sleepSomeTime;

public class ThreadPool {
	
	static AtomicInteger integer = new AtomicInteger(0);
	
	static class MyTask implements Runnable {
		public int id = integer.incrementAndGet();
		public void run() {
			log("Start work item #"+id);
			sleepSomeTime(600, 800);
			log("Finish work item #"+id);
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Executor that keeps a fixed number (3) of threads until it is shut down
		// ExecutorService executor = null; //Executors. ? // INITIAL
//		ExecutorService executor = Executors.newFixedThreadPool(3); // SOLUTION
		
		// TODO Executor that grows the thread pool as necessary, and kills inactive ones after 1 min
		// TODO ExecutorService executor = Executors. ?
//		ExecutorService executor = Executors.newCachedThreadPool(); // SOLUTION
		
		// TODO Executor that have at least 3 thread but can grow up to 10 threads. Inactive threads die in 1 second.
		ExecutorService executor = new ThreadPoolExecutor(3, 10, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(2));// SOLUTION
		// TODO Vary the fixed-sized queue to see it grow the pool and then Rejecting tasks 
		
		for (int i =0;i<40;i++) {
			MyTask task = new MyTask();
			log("Submitted new task #" + task.id);
			executor.execute(task);
			sleepSomeTime(100, 200);
//			queue.offer(new MyTask(), 1, TimeUnit.HOURS);
		}
		// TODO shutdown the executor !
		executor.shutdown();// SOLUTION
	}
}

