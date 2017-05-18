package victor.training.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BasicPlay {

	static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config-basic.xml");

	public static void main(String[] args) {
		System.out.println(applicationContext.getBean("hrService").toString());
	}
	
}
