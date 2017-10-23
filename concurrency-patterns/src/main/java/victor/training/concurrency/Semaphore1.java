package victor.training.concurrency;

import static victor.training.concurrency.ConcurrencyUtil.log;
import static victor.training.concurrency.ConcurrencyUtil.sleep2;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class Semaphore1 {
	static class BoundedHashSet<T> {
		private final Set<T> set;
		private Semaphore sem;
		
		public BoundedHashSet(int bound) {
			set = Collections.synchronizedSet(new HashSet<T>());
			// TODO create Semaphore with given number of permits
			sem = new Semaphore(bound); // SOLUTION
		}
		
		public boolean add(T o) throws InterruptedException {
			// TODO acquire permit from semaphore; if the add failed, release the semaphore back
			// return set.add(o); // INITIAL
			// SOLUTION(
			sem.acquire();
			boolean wasAdded = false;
			try {
				wasAdded = set.add(o);
				return wasAdded;
			} finally {
				if (!wasAdded) {
					sem.release();
				}
			}
			// SOLUTION)
		}
		
		public boolean remove(T o) {
			boolean wasRemoved = set.remove(o);
			// TODO if indeed removed an item, release a virtual permit back to the semaphore
			if (wasRemoved) { // SOLUTION(
				sem.release();
			} // SOLUTION)
			return wasRemoved;
		}
	}
	
	public static void main(String[] args) {
		final BoundedHashSet<String> set = new BoundedHashSet<>(3);
		
		new Thread("Inserter") {
			public void run() {
				addElement("a");
				addElement("b");
				addElement("c");
				addElement("d");
				addElement("e");
			}
			private void addElement(String element) {
				try {
					sleep2(20);
					log("adding element " + element);
					set.add(element);
					log("added");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		
		new Thread("Remover") {
			public void run() {
				sleep2(200);
				remove("a");
				remove("b");
				remove("c");
				remove("d");
				remove("e");
			}
			private void remove(String element) {
				sleep2(20);
				log("removing(" + element + ")");
				set.remove(element);
				log("removed");
			}
		}.start();
		
	}
}
