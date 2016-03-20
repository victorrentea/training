package victor.training.java8.defaultMethod;

public interface InterfaceB {
	void methodB();
	default void defaultMethod() {
		methodB();
		System.out.println("Hello world B");
	}
}
