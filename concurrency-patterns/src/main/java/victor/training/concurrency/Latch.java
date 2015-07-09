package victor.training.concurrency;

import java.util.concurrent.CountDownLatch;

import static victor.training.concurrency.ConcurrencyUtil.log;
import static victor.training.concurrency.ConcurrencyUtil.sleep2;
import static victor.training.concurrency.ConcurrencyUtil.sleepSomeTime;

public class Latch {
	static CountDownLatch startLatch;
	static CountDownLatch finishLatch;
	
	static class Racer extends Thread {
		public void run() {
			positionOnMark();
			waitForGreenLight();
			race();
			reachFinish();
			log("Drive some more");
		}
		
		private void positionOnMark() {
			log("Getting to my position...");
			sleepSomeTime();
		}

		private void waitForGreenLight() {
			try {// SOLUTION
				log("Wait for green light...");
				// TODO wait for the race start signal
				startLatch.await();// SOLUTION
			} catch (InterruptedException e) {} // SOLUTION
		}
		
		private void race() {
			log("Race...");
			sleepSomeTime(100,700);
		}
		
		private void reachFinish() {
			log("Passing finish line");
			// TODO tell that passed finish
			finishLatch.countDown(); // SOLUTION
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		startLatch = new CountDownLatch(1); // SOLUTION
		finishLatch = new CountDownLatch(3); // SOLUTION
		new Racer().start();
		new Racer().start();
		new Racer().start();
		log("Wait some time for the racers to get to their places");
		sleep2(1000);
		log("Ready... Steady... Go!");
		// TODO trigger start race
		startLatch.countDown();// SOLUTION
		
		log("Wait for all to compute classament");
		finishLatch.await(); //SOLUTION
		// TODO wait for all to finish
		log("All have passed finish line");
		log("Classament: blah blah");
	}
	
}
