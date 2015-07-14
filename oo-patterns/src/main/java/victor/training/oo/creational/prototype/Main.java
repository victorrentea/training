package victor.training.oo.creational.prototype;

public class Main {
	// TODO set up the 'template' instance
	//static ConcretePrototype1 P1_TEMPLATE; // = ...; // INITIAL
	static ConcretePrototype1 P1_TEMPLATE = new ConcretePrototype1(); // SOLUTION
	
	public static void main(String[] args) {
		// TODO change to prototyping 
		// ConcretePrototype1 p1 = new ConcretePrototype1(); // INITIAL
		ConcretePrototype1 p1 = P1_TEMPLATE.clone(); // SOLUTION
		
		
	}
}
