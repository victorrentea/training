package victor.training.spring.web.jmx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class Job1 {
	private final static Logger log = LoggerFactory.getLogger(Job1.class);

	@Value("${my.prop:nothing}")
	private String prop;
	
	@Autowired
	private Environment env;
	
	public void doStuff() {
		log.debug("Environment property:" + env.getProperty("my.prop"));
		
		log.debug("Started Job1: " + prop);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		log.debug("Finished Job1");
	}
}
