package victor.perf.stock;

import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.Stream;

import victor.perf.leaks.BigObject200KB;

public class PrimeGenerator {
	private static final BigDecimal TWO = new BigDecimal("2");
	public static BigObject200KB[] bigarr;
	public static BigDecimal[] arr;
	public static Random rand = new Random(); 
	

	public static void main(String[] args) {
		bigarr = new BigObject200KB[10000];
		arr = new BigDecimal[10000];
		Stream.iterate(new BigDecimal("3"), d->d.add(TWO))
//			.parallel()
			.filter(PrimeGenerator::isPrime)
			.limit(20000)
			.forEach(d-> {
//				arr[rand.nextInt(arr.length)] = d;
				bigarr[rand.nextInt(arr.length)] = new BigObject200KB();
				try {
					Thread.sleep(1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				System.out.println(d);
			});
	}
	
	public static boolean isPrime(BigDecimal d) {
		BigDecimal f = new BigDecimal("2");
		while (f.compareTo(d.divide(TWO)) < 0) {
			if (d.remainder(f).equals(BigDecimal.ZERO)) return false;
			f = f.add(BigDecimal.ONE);
		}
		return true;
	}
}
