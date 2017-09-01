package victor.training.oo.behavioral.command;

public class PizzaMan {

	public void bakePizza(String pizzaType, String crustType) {
		System.out.println(Thread.currentThread().getName()+" Baking pizza " + pizzaType + " with crust " + crustType + " ...");
		System.out.println("Done!");
	}
	
}
