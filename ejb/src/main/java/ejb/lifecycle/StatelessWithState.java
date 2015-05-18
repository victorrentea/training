package ejb.lifecycle;

import javax.ejb.Local;

@Local
public interface StatelessWithState {

	void setBusinessParameter(String parameter);
	
	String executeBusiness();
}
