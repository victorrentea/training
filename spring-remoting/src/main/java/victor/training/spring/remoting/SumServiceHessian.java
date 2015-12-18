package victor.training.spring.remoting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SumServiceHessian implements ISumServiceHessian {
	private final static Logger log = LoggerFactory.getLogger(SumServiceHessian.class);

	@Override
	public int sum(int a, int b) {
		int sum = a + b;
		log.debug("Performed sum {} + {} = {}", a, b, sum);
		return sum;
	}

	@Override
	public int sumPair(Pair pair) {
		int sum = pair.getA() + pair.getB();
		log.debug("Performed sum {} + {} = {}", pair.getA(), pair.getB(), sum);
		return sum;
	}

}
