package victor.training.spring.remoting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import victor.training.spring.remoting.ISumServiceHttp;

public class SumServiceHttp implements ISumServiceHttp {
	private final static Logger log = LoggerFactory.getLogger(SumServiceHttp.class);

	@Override
	public int sum(int a, int b) {
		int sum = a + b;
		log.debug("Performed sum {} + {} = {}", a, b, sum);
		return sum;
	}

}
