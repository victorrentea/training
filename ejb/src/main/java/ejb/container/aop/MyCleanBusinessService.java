package ejb.container.aop;

import javax.ejb.Local;

@Local
public interface MyCleanBusinessService {
	
	public void myBusinessCall(String businessParameter);

}
