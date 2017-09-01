package victor.training.oo.behavioral.command;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Waitress {
	private final Executor executor = Executors.newFixedThreadPool(2);
	
	public void takeOrder(Runnable order) {
		executor.execute(order);
	}
}
