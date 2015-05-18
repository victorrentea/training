package ejb.container.aop;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class MyInterceptor {
	@AroundInvoke
	public Object myConcern(InvocationContext context) throws Exception {
		System.out.println("AOP: Before business method");
		Object result = context.proceed();
		System.out.println("AOP: After business method");
		return result;
	}
}
