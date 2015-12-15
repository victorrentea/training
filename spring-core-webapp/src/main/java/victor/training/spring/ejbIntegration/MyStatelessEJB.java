package victor.training.spring.ejbIntegration;

import javax.ejb.Stateless;

@Stateless
public class MyStatelessEJB implements MyStatelessEJBLocal, MyStatelessEJBRemote {

	public void someEjbMethod() {
		System.out.println("In stateless method");
	}

}
