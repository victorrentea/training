package victor.training.jee6.cdi.aop;

public class TheInterfaceImpl implements TheInterface {

	@Override
	public void method1() {
		System.out.println("Original Implementation of Method 1");
	}

	@Override
	public void method2() {
		System.out.println("Original Implementation of Method 2");
	}
}
