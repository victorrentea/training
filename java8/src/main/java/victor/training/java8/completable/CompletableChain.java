package victor.training.java8.completable;

import static java.lang.System.currentTimeMillis;
import static victor.training.java8.parallelstream.ThreadUtils.println;
import static victor.training.java8.parallelstream.ThreadUtils.sleep;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import victor.training.java8.parallelstream.ThreadUtils;

public class CompletableChain {

	public static void doExpensiveStepA() {
		println("Do op A...");
		sleep(2000);
		println("Done A");
	}
	public static void doExpensiveStepB() {
		println("Do op B...");
		sleep(2000);
		println("Done B");
	}
	public static void expensiveStepC() {
		println("Do op C after A&B");
		sleep(2000);
		println("Done C");
	}
	
	public static void main(String[] args) throws Exception {
		println("Start");
		long t0 = currentTimeMillis();
		
		CompletableFuture<Void> asyncA = CompletableFuture.runAsync(CompletableChain::doExpensiveStepA);
		CompletableFuture<Void> asyncB = CompletableFuture.runAsync(CompletableChain::doExpensiveStepB);
		
		CompletableFuture.allOf(asyncA, asyncB)
			.thenRunAsync(CompletableChain::expensiveStepC)
//			.thenRun(CompletableChain::expensiveStepC)
			.join();
		
		
		long t1 = currentTimeMillis();
		println("Took "+(t1-t0));
	}
}
