package victor.training.oo.behavioral.iterator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class IteratorPlay {
	public static void main(String[] args) {
		List<String> collection = Arrays.asList("a", "b", "c");
		Iterator<String> it = collection.iterator();
		while (it.hasNext()) {
			String element = it.next();
			System.out.println("Element: " + element);
		}

		// 2 simpler ... (Iterable)

		// 3 custom iterator

		String[] array = new String[] { "1", "2", "3" };
		Iterator<String> it2 = new MyArrayIterator(array);
		while (it2.hasNext()) {
			String element = it2.next();
			System.out.println("Element: " + element);
		}
	}
}
