package victor.training.java8.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MultiStream {

	public static <T> Stream<List<T>> compose(Stream<T>... streams) {
		List<Iterator<T>> iterators = new ArrayList<>();
		for (Stream<T> stream : streams) {
			iterators.add(stream.iterator());
		}
		return Stream.generate(() -> {
			List<T> element = new ArrayList<>();
			for (Iterator<T> it : iterators) element.add(it.next());
			return element;
		});
	}
	
	public static void main(String[] args) {
		List<Integer> a = Arrays.asList(1,2,3,4,5,6);
		List<Integer> b = Arrays.asList(1,2,3,4,5,6);
		List<Integer> c = Arrays.asList(1,2,3,4,5,6);
		
		System.out.println(compose(a.stream(),b.stream(),c.stream()).map(element->element.get(0)*element.get(1)+element.get(2)).collect(Collectors.toList()));
	}
	
	
	public List<int[]> listOfArraysOfInt = new ArrayList<>();
//	public List<Integer>[] arrayOfListsOfInt = new List<Integer>[2];
}
