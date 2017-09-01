package victor.training.oo.behavioral.command;

public class PizzaOrder implements Runnable{

	private final PizzaMan pizzaMan;
	private final String pizzaType;
	private final String crustType;
	
	public PizzaOrder(PizzaMan pizzaMan, String pizzaType, String crustType) {
		this.pizzaMan = pizzaMan;
		this.pizzaType = pizzaType;
		this.crustType = crustType;
	}

	@Override
	public void run() {
		pizzaMan.bakePizza(pizzaType, crustType);		
	}

}
