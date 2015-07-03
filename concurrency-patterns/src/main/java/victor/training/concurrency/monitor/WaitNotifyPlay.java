package victor.training.concurrency.monitor;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Holder;

import static victor.training.concurrency.ConcurrencyUtil.log;
import static victor.training.concurrency.ConcurrencyUtil.sleep2;

public class WaitNotifyPlay {
	static Holder<String> avizier = new Holder<>();

	public static class Secretary {
		public void publishResults(String results) {
			avizier.value = results;
			
			// TODO avizier.wait();
			synchronized (avizier) { // SOLUTION(
				avizier.notifyAll();
			} // SOLUTION)
			log("Published results");
		}
	}
	
	public static class Student extends Thread {
		public void waitForExamResults() {
			log("Wait for results");
			
			// sleep2(1); // Force thread shift  // INITIAL
			// TODO avizier.wait();
			
			// SOLUTION(
			try { 
//				if (avizier.value == null) {  			
					sleep2(1); 
					// avizier.value==null, apoi secretar:avizier.notifAll(), apoi studentul asta face avizier.wait() ....., nimeni nu va mai face notify
					
					synchronized (avizier) {
						if (avizier.value == null) { 
							sleep2(1);
							avizier.wait();
						} 
					}
//				} 
			} catch (InterruptedException e) {
			} 
			// SOLUTION)
			log("Got results: " + avizier.value);
		}
		
		public void run() {
			
			waitForExamResults();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		long t0 = System.currentTimeMillis();
		
		List<Student> students = new ArrayList<>();
		for (int i = 0 ;i < 100; i++) {
			Student s = new Student();
			s.start();
			students.add(s);
		}
		
		Secretary secretary = new Secretary();
		secretary.publishResults("Exam results");
		
		for (Student s : students) {
			s.join();
		}
		
		
		// TODO measure difference for 10000 students after applying double checked locking pattern
		log("Exection took: " +(System.currentTimeMillis() - t0));
	}
}
