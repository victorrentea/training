package victor.training.ejb.lifecycle;

import javax.ejb.Local;

@Local
public interface StatelessIdentity {

	int getInstanceIdentity();
}
