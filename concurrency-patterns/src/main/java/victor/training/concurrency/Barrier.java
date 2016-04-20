package victor.training.concurrency;

import static victor.training.concurrency.ConcurrencyUtil.log;
import static victor.training.concurrency.ConcurrencyUtil.sleepSomeTime;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Barrier {
	public static final int NO_PARTICIPANTS = 3;
	public static final int NO_DAYS = 5;
	// static CyclicBarrier barrier = null; // TODO // INITIAL
	// SOLUTION(
	static CyclicBarrier barrier = new CyclicBarrier(NO_PARTICIPANTS, new Runnable() {
		int count;
		@Override
		public void run() {
			log("Barrier reached count: " + count++);
			barrier.reset();
		}
	});
	// SOLUTION)
	
	public static void main(String[] args) {
		for (int i=0;i<NO_PARTICIPANTS;i++) {
			new Thread() {
				public void run() {
					for (int i=0; i < NO_DAYS; i++) {
						sleepSomeTime(500, 1000);
						log("Waiting for everyone else to start the stand-up");
						// TODO .await at the barrier
						// SOLUTION(
						try { 
							barrier.await();
						} catch (InterruptedException | BrokenBarrierException e) {
						} // SOLUTION)
						log("Everyone gathered. Stand UP!");
					}
				}
			}.start();
		}
	}
}
