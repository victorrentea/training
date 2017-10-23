package victor.training.concurrency;

import static victor.training.concurrency.ConcurrencyUtil.log;
import static victor.training.concurrency.ConcurrencyUtil.sleep2;
import static victor.training.concurrency.ConcurrencyUtil.sleepSomeTime;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockPhilosophers {
	static class Fork {
		public final int id;
		private final Lock lock = new ReentrantLock();
		public Fork(int id) {
			this.id = id;
		}
		public void take() {
			lock.lock();
		}
		public void putDown() {
			lock.unlock();
		}
	}
	
	static class Phylosopher extends Thread {
		private final Fork leftFork;
		private final Fork rightFork;
		
		public Phylosopher(String name, Fork leftFork, Fork rightFork) {
			super(name);
			this.leftFork = leftFork;
			this.rightFork = rightFork;
		}

		public void run() {
			Fork firstFork = leftFork;
			Fork secondFork = rightFork;
			if (firstFork.id > secondFork.id) { // SOLUTION(
				firstFork = rightFork;
				secondFork = leftFork;
			} // SOLUTION)
			for (int i=0;i<50;i++) {
				sleepSomeTime();
				log("I'm hungry!");
				
				log("Waiting for first fork (" + firstFork.id + ")");
				firstFork.take();
				log("Took it");
				sleepSomeTime();
				log("Taking second fork (" + secondFork.id + ")");
				secondFork.take();
				
				eat();
				
				firstFork.putDown();
				sleepSomeTime();
				secondFork.putDown();
				log("Put down forks. Thinking...");
			}
		}

		private void eat() {
			log("Took both forks. Eating...");
			sleepSomeTime();
			log("I had enough. I'm putting down the forks");
		}
	}
	
	public static void main(String[] args) {
		Fork[] forks = new Fork[] {new Fork(1), new Fork(2), new Fork(3), new Fork(4), new Fork(5)};
		new Phylosopher("Plato", forks[0], forks[1]).start();
		new Phylosopher("Konfuzius", forks[1], forks[2]).start();
		new Phylosopher("Socrates", forks[2], forks[3]).start();
		new Phylosopher("Voltaire", forks[3], forks[4]).start();
		sleep2(1000);
		new Phylosopher("Descartes", forks[4], forks[0]).start();
	}
}
