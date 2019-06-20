package victor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.IntFunction;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 2, time = 2, timeUnit = TimeUnit.SECONDS)
public class Streams {
	
	private static List<Integer> numbers() {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 1_000_000; i++) {
			list.add(i);
		}
		return list;
	}
	
	@Benchmark
	public void streams() {
		int sum = numbers().stream().mapToInt(i -> i).sum();
		System.out.println("sum: " + sum);
	}
	@Benchmark
	public void forloop() {
		int sum = 0;
		for (int i:numbers()) {
			sum += i;
		}
		System.out.println("sum: " + sum);
	}

}
