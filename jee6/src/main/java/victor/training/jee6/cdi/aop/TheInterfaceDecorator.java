package victor.training.jee6.cdi.aop;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

@Decorator
public class TheInterfaceDecorator implements TheInterface {
	@Inject
	@Delegate
//	@Any
	private TheInterface delegate;

	@Override
	public void method1() {
		System.out.println("Decorator before Method 1");
		delegate.method1();
		System.out.println("Decorator after Method 1");
	}

	@Override
	public void method2() {
		System.out.println("Decorator before Method 2");
		delegate.method2();
		System.out.println("Decorator after Method 2");

	}

}
