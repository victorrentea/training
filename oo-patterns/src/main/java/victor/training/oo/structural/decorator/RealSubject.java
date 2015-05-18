package victor.training.oo.structural.decorator;

public class RealSubject implements Subject {
	@Override
	public void methodA() {
		System.out.println("RealSubject: In metoda A");
	}

	@Override
	public void methodB() {
		System.out.println("RealSubject: In metoda B");
	}
}
