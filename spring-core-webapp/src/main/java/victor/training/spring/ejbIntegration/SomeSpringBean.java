package victor.training.spring.ejbIntegration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SomeSpringBean {
	@Autowired
	private MyStatelessEJBLocal localEjb;
	
	@Autowired
	private MyStatelessEJBRemote remoteEjb;
	
	public void someSpringMethod() {
		System.out.println("In Spring bean method");
		System.out.println("Invoking local EJB");
		localEjb.someEjbMethod();
		System.out.println("Invoking remote EJB");
		remoteEjb.someEjbMethod();
	}
}
