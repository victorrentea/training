package victor.training.spring.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Main {
	@Autowired
	private MyGateway myGateway;

	public void run() {
		String reply = myGateway.send("Text");
		System.out.println("Reply :  "+reply);
	}

	
	public static void main(String[] args) throws InterruptedException {
		new ClassPathXmlApplicationContext("applicationContext.xml").getBean(Main.class).run();
		
		Thread.sleep(2000);
	}
}
