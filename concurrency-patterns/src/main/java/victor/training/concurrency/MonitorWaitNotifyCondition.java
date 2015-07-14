package victor.training.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static victor.training.concurrency.ConcurrencyUtil.log;
import static victor.training.concurrency.ConcurrencyUtil.sleepSomeTime;

public class MonitorWaitNotifyCondition {
	static Drop drop = new Drop();

	static class Drop {
	    private int message;
	    
	    // true = consumer should wait for producer to send message
	    // false = producer should wait for consumer to retrieve message
	    private boolean condition = true;

	    public synchronized int take() {
	        // Wait until message is available.
	        while (condition) {
	            try {
	                wait();
	            } catch (InterruptedException e) {}
	        }
	        condition = true;
	        
	        // Notify producer that status has changed.
	        notifyAll(); 
	        
	        return message;
	    }

	    public synchronized void put(int message) {
	        // Wait until message has been retrieved.
	    	while (!condition) {
	            try {
	                wait();
	            } catch (InterruptedException e) {}
	        }
	    	condition = false;
	    	
	    

	        this.message = message; 
	        
	        // Notify consumer that status has changed.
	        notifyAll();
	    }
	}
	
	static AtomicInteger receivedCounter = new AtomicInteger();
	
	static class Producer implements Runnable {
		public void run() {
			for (int i = 0; i < 50; i++) {
				log("Putting message: " + i);
				drop.put(i);
				sleepSomeTime();
			}
		}
	}
	
	static class Consumer implements Runnable {
		public void run() {
			while (receivedCounter.incrementAndGet() <= 50) {
				log("Taking message ...");
				int message = drop.take();
				log("Took message: " + message);
				sleepSomeTime();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		List<Thread> threads = new ArrayList<>();
		threads.add(new Thread(new Producer(), "Producer-1"));
		threads.add(new Thread(new Consumer(), "Consumer-1"));
		threads.add(new Thread(new Consumer(), "Consumer-2"));
		threads.add(new Thread(new Consumer(), "Consumer-3"));
		threads.add(new Thread(new Consumer(), "Consumer-4"));
		for (Thread t : threads) {
			t.start();
		}
		for (Thread t : threads) {
			t.join();
		}
		int missedMessages = 50 - receivedCounter.get();
		if (missedMessages > 0) {
			System.err.println("Missed messages: " + missedMessages);
		} else {
			System.out.println("All ok !");
		}
	}
}

