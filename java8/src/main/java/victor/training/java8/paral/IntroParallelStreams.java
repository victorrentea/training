package victor.training.java8.paral;

import static victor.training.java8.paral.ThreadUtils.sleep;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

public class IntroParallelStreams {
	public static void main(String[] args) {
		Stream.of(1, 2, 3, 4, 5, 6)
			.map(i -> {
				sleep(100);
				ThreadUtils.println("map " + i );
				return i;
			})
			.parallel()
			.forEach(i -> {
				sleep(100);
				ThreadUtils.println("out " + i );
			});

		System.out.println("Common FJP size: " + ForkJoinPool.commonPool().getPoolSize());
		System.out.println("Number or processors: " + Runtime.getRuntime().availableProcessors());

	}
}