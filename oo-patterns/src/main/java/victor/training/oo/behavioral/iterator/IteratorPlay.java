package victor.training.oo.behavioral.iterator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class IteratorPlay {
	public static void main(String[] args) {
		List<String> collection = Arrays.asList("a", "b", "c");
		// TODO #1 iterate over collection with Iterator
		// while (it.hasNext()) {...
		Iterator<String> it = collection.iterator(); // SOLUTION(
		while (it.hasNext()) {
			String element = it.next();
			System.out.println("Element: " + element);
		} // SOLUTION)

		// TODO #2 switch to using java5 foreach (Iterable)
		// for (String element : ...)

		// TODO #3 create a custom iterator over arrays
		String[] array = new String[] { "1", "2", "3" };
		// Iterator<String> it2 = null; // TODO // INITIAL
		Iterator<String> it2 = new MyArrayIterator(array); // SOLUTION
		while (it2.hasNext()) {
			String element = it2.next();
			System.out.println("Element: " + element);
		}
	}
}
