package victor.training.oo.structural.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import victor.training.oo.structural.decorator.Subject;

public class DynamicProxyInvocationHandler implements InvocationHandler {

	private final Subject target;
	
	public DynamicProxyInvocationHandler(Subject target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("In DynamicProxy, intercepted method call : " + method.getName());
		
		return method.invoke(target, args);
	}

}
