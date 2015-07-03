package victor.training.concurrency.barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static victor.training.concurrency.ConcurrencyUtil.log;
import static victor.training.concurrency.ConcurrencyUtil.sleepRandom;

public class CyclicBarrierPlay {
	
	// static CyclicBarrier barrier = null; // TODO // INITIAL
	// SOLUTION(
	static CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
		int count;
		@Override
		public void run() {
			log("Barrier reached count: " + count++);
			barrier.reset();
		}
	});
	// SOLUTION)
	
	public static void main(String[] args) {
		for (int i=0;i<3;i++) {
			new Thread() {
				public void run() {
					for (int i=0; i< 4; i++) {
						sleepRandom(500, 1000);
						log("Arrived at the barrier");
						try {
							barrier.await();
						} catch (InterruptedException | BrokenBarrierException e) {
						}
						log("Got past the barrier");
					}
				}
			}.start();
		}
	}
}
