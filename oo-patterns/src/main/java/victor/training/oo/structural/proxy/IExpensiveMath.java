package victor.training.oo.structural.proxy; // SOLUTION

import java.math.BigDecimal;

public interface IExpensiveMath {

	BigDecimal sqrt(BigDecimal A, final int SCALE);

	boolean isPrime(BigDecimal number);

}
