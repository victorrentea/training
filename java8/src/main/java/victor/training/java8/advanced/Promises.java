package victor.training.java8.advanced;

import java.util.concurrent.CompletableFuture;

import javax.swing.Timer;

public class Promises {
	
	public static void main(String[] args) throws InterruptedException {
		CompletableFuture<String> stringPromise = new CompletableFuture<String>();
		
		stringPromise
			.thenApply(String::trim)
			.thenApplyAsync(Promises::toIntegerRemote)
			.thenAccept(i -> {log("number squared: " + i*i);});
		
		Timer timer = new Timer(2500, e -> {
			log("Timer rings: completing the Promise");
			stringPromise.complete("  999  ");
		});
		timer.start();
		
		log("All set up, waiting for promises to execute in background");
		Thread.sleep(4000);
		log("Terminated");
	}
	
	public static Integer toIntegerRemote(String s) {
		log("Spending 1 second to do the toInteger operation");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Integer.parseInt(s);
	}
	

	public static void log(String message) {
		System.out.println(String.format("[%s] %s", Thread.currentThread().getName(), message));
	}
	
}
