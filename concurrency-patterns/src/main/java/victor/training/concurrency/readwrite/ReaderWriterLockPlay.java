package victor.training.concurrency.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import static victor.training.concurrency.ConcurrencyUtil.log;
import static victor.training.concurrency.ConcurrencyUtil.sleepRandom;

public class ReaderWriterLockPlay {
	static ReentrantReadWriteLock lock = new ReentrantReadWriteLock(); // SOLUTION
	
	static String blackboard = "nimic";
	
	static class Student extends Thread {
		public Student() {
			setName("Student" + getName());
		}
		public void run() {
			for (int i = 0; i < 5; i++) {
				sleepRandom(50, 200);
				log("Waiting for Read lock"); // SOLUTION
				lock.readLock().lock(); // SOLUTION
				log("Started reading");
				for (int j = 0; j < 3; j++) {
					sleepRandom(10, 50);
					log("Reading:" + blackboard);
				}
				log("Finished reading");
				sleepRandom(10, 50);
				lock.readLock().unlock(); // SOLUTION
				log("Release Read lock"); // SOLUTION
			}
			super.run();
		}
	}
	
	static class Professor extends Thread {
		public Professor() {
			setName("Professor" + getName());
		}
		public void run() {
			for (int i = 0; i < 5; i++) {
				sleepRandom(50, 200);
				log("Waiting for Write lock"); // SOLUTION
				lock.writeLock().lock(); // SOLUTION
				log("Stared writing");
				sleepRandom(10, 50);
				blackboard = "Cuvant" + i;
				for (int j = 0; j < 3; j++) {
					sleepRandom(10, 50);
					log("Writing:" + blackboard);
				}
				log("Finished writing");
				lock.writeLock().unlock(); // SOLUTION
				log("Release Write lock"); // SOLUTION
			}
			super.run();
		}
	}
	
	public static void main(String[] args) {
		new Student().start();
		new Student().start();
		new Student().start();
		new Professor().start();
		new Professor().start();
	}
}

