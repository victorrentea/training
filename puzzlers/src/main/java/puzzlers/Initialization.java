package puzzlers;

public class Initialization extends Parent {

	private boolean test = false;

	public Initialization() {
		super();
		System.out.println("Test Value" + test);
	}
	

	public void init() {
		test = true;
	}

	public static void main(String[] args) {
		new Initialization();
	}
}

class Parent {

	public Parent() {
		init();
	}

	public void init() {
	}
}