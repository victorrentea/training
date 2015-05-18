package victor.training.oo.structural.decorator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DecoratorPlay {
	public static void main(String[] args) throws Exception {
		Subject realSubject = new RealSubject();

		Subject decorator = new Decorator(realSubject);
		codClient(decorator);

		// Decorator: unmodifiableList
		List<String> list = new ArrayList<>(Arrays.asList("a", "b"));
		List<String> newList = Collections.unmodifiableList(list);
		newList.add("new Element"); // to throw exception
	}

	private static void codClient(Subject subject) {
		System.out.println("Cod client: Inainte de a apela metoda A");
		subject.methodA();
		System.out.println("Cod client: Dupa apelarea metodei A");
	}
}
