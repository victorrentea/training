package victor.training.spring.basic;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpelPlay {

	static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config-spel.xml");

	public static void main(String[] args) {
		System.out.println(applicationContext.getBean("spelSandbox").toString());
	}
	
}
