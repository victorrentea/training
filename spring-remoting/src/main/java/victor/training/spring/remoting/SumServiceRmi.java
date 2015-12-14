package victor.training.spring.remoting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import victor.training.spring.remoting.ISumServiceRmi;

public class SumServiceRmi implements ISumServiceRmi {
	private final static Logger log = LoggerFactory.getLogger(SumServiceRmi.class);

	@Override
	public int sum(int a, int b) {
		int sum = a + b;
		log.debug("Performed sum {} + {} = {}", a, b, sum);
		return sum;
	}

}
