package victor;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
public class SBTest {
	String a = "a";
	String b = "b";

	@Benchmark
	public void stringConcat() {
		String c = a + b;
		
	}
	@Benchmark
	public void stringBuilderAppend() {
		StringBuilder cb = new StringBuilder();
		cb.append(a);
		cb.append(b);
		String c = cb.toString();
	}
	
	

	
	void concateneazalepetoate(List<Integer> numere, Writer oriundeAiVreaMatalutzaSaScrii) throws IOException {
//		String s ="";
		for (int numar : numere) {
//			s += numar;
			oriundeAiVreaMatalutzaSaScrii.write(String.valueOf(numar));
		}
//		return s;
	}

}
 