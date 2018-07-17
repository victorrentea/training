package victor.training.java8.completable;

import static java.lang.System.currentTimeMillis;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import victor.training.java8.parallelstream.ThreadUtils;

public class CompletableJoin {

	private static int expensiveStepA() {
		ThreadUtils.println("Call web service A...");
		ThreadUtils.sleep(2000);
		ThreadUtils.println("Done A");
		return 2;
	}
	private static int expensiveStepB() {
		ThreadUtils.println("Call web service B...");
		ThreadUtils.sleep(2000);
		ThreadUtils.println("Done B");
		return 3;
	}
	private static void expensiveStepC(int aPlusB) {
		ThreadUtils.println("Call web service C: " + aPlusB);
		ThreadUtils.sleep(2000);
		ThreadUtils.println("Done C");
	}
	
	// public static void main(String[] args) { // INITIAL
	public static void main(String[] args) throws InterruptedException, ExecutionException { // SOLUTION
		long t0 = currentTimeMillis();
		
//		int a = expensiveStepA(); INITIAL(
//		int b = expensiveStepB();
//		expensiveStepC(a+b); INITIAL)
		
		// SOLUTION(
		CompletableFuture<Integer> asyncA = CompletableFuture.supplyAsync(CompletableJoin::expensiveStepA);
		CompletableFuture<Integer> asyncB = CompletableFuture.supplyAsync(CompletableJoin::expensiveStepB);
		
		CompletableFuture.allOf(asyncA, asyncB).join();
		
		expensiveStepC(asyncA.get() + asyncB.get());
		// SOLUTION)
		
		long t1 = currentTimeMillis();
		ThreadUtils.println("Took "+(t1-t0));
	}
}
