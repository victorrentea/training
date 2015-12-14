package victor.training.spring.rmi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import victor.training.spring.remoting.ISumServiceRmi;

@Service
public class SumServiceClient {

	@Autowired
	private ISumServiceRmi sumServiceRmi;
	
	@Autowired
	private ISumServiceRmi sumServiceHttp;
	
	private void execute() {
		System.out.println("Got response via RMI: " + sumServiceRmi.sum(1, 2));
		System.out.println("Got response via HTTP: " + sumServiceHttp.sum(1, 2));
	}
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("client-config.xml");
		applicationContext.getBean(SumServiceClient.class).execute();
	}

}
