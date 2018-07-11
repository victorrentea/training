package victor.training.java8.paral;

import static java.util.stream.Collectors.toSet;

import java.util.Set;
import java.util.stream.IntStream;

public class ParalOverhead {

	public static void main(String[] args) {
		int v = IntStream.range(0, 10_000).mapToObj(i->i).collect(toSet()).stream().reduce(0, (a,b) -> a + b);

		long t0 = System.currentTimeMillis();
		int sum = 0;
		for (int j = 0; j < 1000; j++) {
			Set<Integer> numbers = IntStream.range(0, 1_000).mapToObj(i->i).collect(toSet());
			sum += numbers.stream()
//				.parallel()
				.sorted()
				.reduce(0, (a,b) -> a + b);
		}
		long t1 = System.currentTimeMillis();
		System.out.println("Sum = " + sum + " took " + (t1-t0));
	}
}
