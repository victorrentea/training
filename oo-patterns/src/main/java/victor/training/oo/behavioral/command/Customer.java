package victor.training.oo.behavioral.command;

public class Customer {
	
	private final PizzaMan pizzaMan;
	
	public Customer(PizzaMan pizzaMan) {
		this.pizzaMan = pizzaMan;
	}

	public void act() {
		System.out.println("Shouting for a pizza!");
		pizzaMan.bakePizza("Capriciosa", "thin");
	}
	
	public static void main(String[] args) {
		new Customer(new PizzaMan()).act();
	}
}
