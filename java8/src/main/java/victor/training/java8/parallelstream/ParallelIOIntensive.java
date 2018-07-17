package victor.training.java8.parallelstream;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class ParallelIOIntensive {

	private static boolean stepIOBound(Integer i) {
		ThreadUtils.println("Doing work");
		ThreadUtils.sleep(1000);
		return true;
	}
	
	public static void main(String[] args) throws InterruptedException {
		long t0 = System.currentTimeMillis();
		List<Integer> list = asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		Stream<Integer> stream = list.parallelStream()
			.filter(ParallelIOIntensive::stepIOBound)
			.map(n -> n * n);
		executeInCustomFJP(() -> {// SOLUTION
			stream.forEachOrdered(System.out::println);
		}); // SOLUTION
		long delta = System.currentTimeMillis() - t0;
		System.out.println("Took " + delta);
	}
	
	// SOLUTION(
	public static void executeInCustomFJP(Runnable r) throws InterruptedException {
		ForkJoinPool pool = new ForkJoinPool(10);
		pool.submit(r);
		Stream<Object> ss = Stream.of("a");
		
		pool.submit(() -> ss.forEach(System.out::println));
		
		pool.shutdown();
		pool.awaitTermination(10, TimeUnit.SECONDS);
		
	} // SOLUTION)
}
