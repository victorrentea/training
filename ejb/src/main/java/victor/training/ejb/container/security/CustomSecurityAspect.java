package victor.training.ejb.container.security;

import java.lang.reflect.Method;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class CustomSecurityAspect {
	
	@AroundInvoke
	public Object customSecurityConcern(InvocationContext context) throws Exception {
		Method method = context.getMethod();
		if (isCallAllowed(method)) {
			return context.proceed();
		} else {
			throw new IllegalAccessError();
		}
	}
	
	private boolean isCallAllowed(Method method) {
		///
		return true;
	}
}
