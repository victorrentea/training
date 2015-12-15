package victor.training.spring.ejbIntegration;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

@Singleton
@Startup
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class SingletonEJB {

	@Autowired
	private SomeSpringBean someSpringBean;
	
	@PostConstruct 
	public void invokeSpringBean() {
		System.out.println("In singleton method (at startup)");
		someSpringBean.someSpringMethod();
	}

}
