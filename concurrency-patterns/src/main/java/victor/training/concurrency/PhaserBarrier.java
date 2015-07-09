package victor.training.concurrency;

import java.util.concurrent.Phaser;

import static victor.training.concurrency.ConcurrencyUtil.log;
import static victor.training.concurrency.ConcurrencyUtil.sleepSomeTime;

public class PhaserBarrier {
	static Phaser phaser = new Phaser();
	
	static class Car extends Thread {
		
		public Car(String name) {
			super(name);
		}
		public void run() {
			log("Arrived at the barrier, in phase " + phaser.getPhase());
			int phase = phaser.arriveAndAwaitAdvance();
			log("After 1st barrier, we are in phase "+phase);
			sleepSomeTime(10, 1000);
			log("Arrived at the 2nd barrier, in phase " + phaser.getPhase());
			phase = phaser.arriveAndAwaitAdvance();
			log("After 2nd barrier, we are in phase "+phase);
		}
	}
	
	public static void main(String[] args) {
		log("Starting phase " + phaser.getPhase());
		phaser.register();
		startAndRegister(new Car("Car1"));
		startAndRegister(new Car("Car2"));
		phaser.arriveAndDeregister(); 
	}
	
	static void startAndRegister(Thread t) {
		t.start();
		phaser.register();
	}
}


