package victor.training.java8.cleanlambda.talk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class DynamicProxy {

	interface I {
		Integer mxxx(Integer x) ;
	}
	
	public static void main(String[] args) {
		InvocationHandler handler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println(" fraieru a vrut sa invoce "  + method.getName() + " cu paramterii " + Arrays.toString(args));
				return null;
			}
		};
		
		
		
		I oInstanta = (I) Proxy.newProxyInstance(DynamicProxy.class.getClassLoader(), 
				new Class<?>[] { I.class } , handler);
				
		oInstanta.mxxx(111);
	}
}
