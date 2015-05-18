package victor.training.oo.structural.decorator;

public class Decorator implements Subject {

	private final Subject delegate;
	
	public Decorator(Subject delegate) {
		this.delegate = delegate;
	}

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
}
