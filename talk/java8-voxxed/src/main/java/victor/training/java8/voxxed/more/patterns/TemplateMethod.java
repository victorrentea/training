package victor.training.java8.voxxed.more.patterns;

import java.util.function.Consumer;

public class TemplateMethod {

	public static class MyResource { }
	
	public static void withResource(Consumer<MyResource> consumer) {
		try {
			System.out.println("Open connection");
			System.out.println("Prepare resource");
			MyResource resource = new MyResource();
			consumer.accept(resource);
		} catch (RuntimeException e) {
			System.err.println("Careful logging");
		} finally {
			System.out.println("Release resource");
		}
	}
	
	public static void main(String[] args) {
		withResource(resource -> {System.out.println("Do stuff with resource " + resource);});
	}
}
