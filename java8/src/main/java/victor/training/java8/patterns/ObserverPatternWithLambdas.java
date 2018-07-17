package victor.training.java8.patterns;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ObserverPatternWithLambdas {
	@FunctionalInterface
	public interface EventHandler {
		void notify(String event);
	}
	
	private List<EventHandler> observers = new ArrayList<>();
	
	public void addEventHandler(EventHandler handler) {
		observers.add(handler);
	}
	
	public void removeEventHandler(EventHandler handler) {
		observers.remove(handler);
	}
	
	public void fire() {
		for (EventHandler observer : observers) {
			observer.notify("Event now: " + new SimpleDateFormat("hh:mm:ss").format(new Date()));
		}
	}
	
	public static void main(String[] args) {
		ObserverPatternWithLambdas subject = new ObserverPatternWithLambdas();
		subject.addEventHandler(event -> System.out.println(event));
		subject.fire();
	}
}
