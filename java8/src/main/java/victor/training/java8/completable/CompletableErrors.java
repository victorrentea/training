package victor.training.java8.completable;

import java.util.concurrent.CompletableFuture;

public class CompletableErrors {
	
	Integer step1() {
		return 1;
	}
	Integer step2(Integer in) {
//		throw new IllegalArgumentException();
		return in * 2;
	}
	String step3(Integer in) {
		return "X" + in + "X";
	}
	
	private void run() {
		CompletableFuture<String> c = CompletableFuture
			.supplyAsync(this::step1)
			.thenApply(this::step2)
			.exceptionally(e -> 5)
			.thenApply(this::step3);
		String rez = c.join();
		System.out.println("Got: " + rez);
	
		
	}
public static void main(String[] args) {
	new CompletableErrors().run();
}

}
