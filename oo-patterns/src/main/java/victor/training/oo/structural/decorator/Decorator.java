package victor.training.oo.structural.decorator;

public class Decorator implements Subject { // SOLUTION
//public class Decorator { //implements ?? { // INITIAL

	private final Subject delegate;
	
	public Decorator(Subject delegate) {
		this.delegate = delegate;
	}
	
	// TODO intercept both method calls and print message in console before/after delegating to the appropriate method in delegate.

	// SOLUTION(
	@Override
	public void methodA() {
		System.out.println("Decorator: inainte de metoda A");
		delegate.methodA();
		System.out.println("Decorator: dupa metoda A");
	}

	
	@Override
	public void methodB() {
		System.out.println("Decorator: inainte de metoda B");
		delegate.methodB();
		System.out.println("Decorator: inainte de metoda B");
	}
	// SOLUTION)
}
