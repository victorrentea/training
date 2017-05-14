package victor.training.spring.basic.web.jmx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Job1 {
	private final static Logger log = LoggerFactory.getLogger(Job1.class);

	public void doStuff() {
		log.debug("Started Job1");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		log.debug("Finished Job1");
	}
}
