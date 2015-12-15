package victor.training.spring.ejbIntegration;

import javax.ejb.Local;

@Local
public interface MyStatelessEJBLocal {

	void someEjbMethod();

}
