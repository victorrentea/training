package victor.training.concurrency.basic;

import victor.training.concurrency.ConcurrencyUtil;
import static victor.training.concurrency.ConcurrencyUtil.log;

// Inspired from http://java.dzone.com/articles/java-volatile-keyword-0
public class VolatilePlay {
	// private static int GLOBAL = 0; // INITIAL
	private static volatile int GLOBAL = 0; // SOLUTION

	public static void main(String[] args) {
		new ChangeListener().start();
		new ChangeMaker().start();
	}

	static class ChangeListener extends Thread {
		public void run() {
			int lastValue = GLOBAL;
			while (lastValue < 5) {
				if (lastValue != GLOBAL) {
					log("Got Change for GLOBAL : " + GLOBAL);
					lastValue = GLOBAL;
				}
//				ConcurrencyUtil.sleep(1); // this 'fixes' the problem, even without volatile
				// TODO test the above statement
			}
		}
	}

	static class ChangeMaker extends Thread {
		public void run() {
			while (GLOBAL < 5) {
				log("Incrementing GLOBAL to " +( GLOBAL + 1));
				GLOBAL ++;
				ConcurrencyUtil.sleep2(500);
			}
		}
	}
}