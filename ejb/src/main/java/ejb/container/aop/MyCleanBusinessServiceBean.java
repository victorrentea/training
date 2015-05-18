package ejb.container.aop;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

@Stateless
@Interceptors(MyInterceptor.class)
public class MyCleanBusinessServiceBean implements MyCleanBusinessService {

	@Override
	public void myBusinessCall(String businessParameter) {
		System.out.println("Executing business");
	}

}
