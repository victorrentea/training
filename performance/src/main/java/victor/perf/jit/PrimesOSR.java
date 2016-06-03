package victor.perf.jit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


// -XX:+PrintCompilation -XX:+PrintCodeCache -XX:ReservedCodeCacheSize=2m  -XX:InitialCodeCacheSize=500k
public class PrimesOSR {
	private static final BigDecimal TWO = new BigDecimal("2");
	private static final int NUMBER_OF_PRIMES = 4000;

	public static void main(String[] args) throws IOException {
		List<BigDecimal> primes = new ArrayList<>();
		primes.add(TWO);
		BigDecimal tentative = new BigDecimal("3");
		
		Writer writer = new FileWriter(new File("out.txt"));
		
		while (primes.size() < NUMBER_OF_PRIMES) {
			boolean isPrime = true;
			for (BigDecimal factor : primes) {
				if (tentative.remainder(factor).equals(BigDecimal.ZERO)) {
					isPrime = false;
					break;
				}
			}
			if (isPrime) {
				primes.add(tentative);
				if (primes.size() % 100 == 0) {
					System.out.println("Prime [" + primes.size()+ "] = " + tentative);
				}
				writer.write("" + tentative);
			}
			tentative = tentative.add(TWO);
		}
		writer.close();
	}
}
