package victor.training.java8.completable;

import static java.lang.System.currentTimeMillis;
import static victor.training.java8.parallelstream.ThreadUtils.println;
import static victor.training.java8.parallelstream.ThreadUtils.sleep;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import victor.training.java8.parallelstream.ThreadUtils;

public class CompletableIntroElegant {

	private static int expensiveStepA() {
		println("Call web service A...");
		sleep(2000);
		println("Done A");
		return 2;
	}
	private static int expensiveStepB() {
		println("Call web service B...");
		sleep(2000);
		println("Done B");
		return 3;
	}
	private static void expensiveStepC(int aPlusB) {
		println("Call web service C: " + aPlusB);
		sleep(2000);
		println("Done C");
	}
	
	public static void main(String[] args) throws Exception {
		println("Start");
		long t0 = currentTimeMillis();
		
		CompletableFuture<Integer> asyncA = CompletableFuture.supplyAsync(CompletableIntroElegant::expensiveStepA);
		CompletableFuture<Integer> asyncB = CompletableFuture.supplyAsync(CompletableIntroElegant::expensiveStepB);
		
		CompletableFuture.allOf(asyncA, asyncB).join();
		
		expensiveStepC(asyncA.get() + asyncB.get());
		long t1 = currentTimeMillis();
		println("Took "+(t1-t0));
	}
}
