package victor.training.oo.behavioral.iterator;

import static java.util.Arrays.asList;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamVsIterator {
	public static void main(String[] args) {
		List<Integer> list = asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		
		// Make an Iterable
		Iterable<Integer> iterable = () -> list.iterator(); // SOLUTION
		
		// Make Stream using StreamSupport
		Stream<Integer> stream = StreamSupport.stream(iterable.spliterator(), false); // SOOLUTION
		
		stream.forEach(System.out::println); // SOLUTION
		
		// Now, vice-versa:
		Stream<Integer> st = list.stream();
		
		// Convert to Iterator and get first element
		System.out.println(st.iterator().next()); // SOLUTION
	}
}
