package victor.training.ejb.lifecycle;

import javax.ejb.Local;

@Local
public interface PoolSize {

	void method(int sleepMillis);
	
	int getInstanceCount();
}
