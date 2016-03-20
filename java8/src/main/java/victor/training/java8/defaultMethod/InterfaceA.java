package victor.training.java8.defaultMethod;

public interface InterfaceA {
	void methodA();
	default void defaultMethod() {
		methodA();
		System.out.println("Hello world B");
	}
}
