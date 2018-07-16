package victor.training.java8.paral;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CpuContention {
	static boolean isPrime(BigDecimal n) {
		BigDecimal nDiv2 = n.divide(new BigDecimal(2));
		//		ThreadUtils.println("testing " +n);
		for (BigDecimal i = new BigDecimal(2); i.compareTo(nDiv2)< 0; i = i.add(BigDecimal.ONE)) {
			if (n.divideAndRemainder(i)[1].equals(BigDecimal.ZERO)) {
				return false;
			}
		}
		return true;
	}
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ThreadUtils.timerStart();
		Stream<BigDecimal> primeStream = IntStream.range(40000, 50000)
//		Stream<BigDecimal> primeStream = IntStream.range(10010000, 10020000)
			.parallel()
			.mapToObj(i->new BigDecimal(i))
			.filter(CpuContention::isPrime)
			;
		
		ForkJoinPool pool = new ForkJoinPool(6);
		Long n = pool.submit(() ->primeStream.count()).get();
		pool.shutdownNow();
		pool.awaitTermination(1, TimeUnit.MINUTES);
		ThreadUtils.timerEndPrint();
		System.out.println("Found: " +n);
		
	}
}
