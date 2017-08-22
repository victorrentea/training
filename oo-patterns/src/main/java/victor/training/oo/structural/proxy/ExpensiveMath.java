package victor.training.oo.structural.proxy;

import java.math.BigDecimal;
import java.math.RoundingMode;

//public class ExpensiveMath { // INITIAL
public class ExpensiveMath implements IExpensiveMath { // SOLUTION
	
	private BigDecimal TWO = new BigDecimal("2");
	
	public boolean isPrime(BigDecimal number) { // silly implem
		if (number.compareTo(TWO) <= 0) {
			return true;
		}
		if (number.remainder(TWO).equals(BigDecimal.ZERO)) {
			return false;
		}
		for (BigDecimal divisor = new BigDecimal("3"); 
			divisor.compareTo(number.divide(new BigDecimal("2"))) < 0;
			divisor = divisor.add(TWO)) {
			if (number.remainder(divisor).equals(BigDecimal.ZERO)) {
				return false;
			}
		}
		return true;
	}

	public BigDecimal sqrt(BigDecimal A, final int SCALE) {
	    BigDecimal x0 = new BigDecimal("0");
	    BigDecimal x1 = new BigDecimal(Math.sqrt(A.doubleValue()));
	    while (!x0.equals(x1)) {
	        x0 = x1;
	        x1 = A.divide(x0, SCALE, RoundingMode.HALF_UP);
	        x1 = x1.add(x0);
	        x1 = x1.divide(TWO, SCALE, RoundingMode.HALF_UP);
	    }
	    return x1;
	}
}
