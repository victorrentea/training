package victor.training.oo.behavioral.command;

public class CustomerRespectful {
	
	private final Waitress waitress;
	private final PizzaMan pizzaMan;
	
	public CustomerRespectful(Waitress waitress, PizzaMan pizzaMan) {
		this.waitress = waitress;
		this.pizzaMan = pizzaMan;
	}

	public void act() {
		System.out.println("Shouting for a pizza!");
		waitress.takeOrder(new PizzaOrder(pizzaMan, "Capriciosa", "thin"));
	}
	
	public static void main(String[] args) {
		Waitress waitress = new Waitress();
		PizzaMan pizzaMan = new PizzaMan();
		new CustomerRespectful(waitress, pizzaMan).act();
	}
}
