package victor.training.spring.basic;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import victor.training.spring.basic.service.HRService;

public class AopPlay {

	static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config-basic.xml");

	public static void main(String[] args) {
		System.out.println("---- remove employee -----");
		applicationContext.getBean(HRService.class).removeEmployee("12345");
		System.out.println("---- get employee -----");
		applicationContext.getBean(HRService.class).getEmployeeById("12345");
	}
	
}
