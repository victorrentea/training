package victor.training.java8.streams;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/*
Convert elements of a collection to upper case.
 */
public class ToUpperCaseSpec {

	public List<String> transform7(List<String> collection) {
		List<String> newCollection = new ArrayList<>();
		for (String element : collection) {
			newCollection.add(element.toUpperCase());
		}
		return newCollection;
	}

	public List<String> transform(List<String> collection) {
		return collection.stream() // Convert collection to Stream
				.map(String::toUpperCase) // Convert each element to upper case
				.collect(toList()); // Collect results to a new list
	}

	@Test
	public void transformShouldConvertCollectionElementsToUpperCase() {
		List<String> collection = asList("My", "name", "is", "John", "Doe");
		List<String> expected = asList("MY", "NAME", "IS", "JOHN", "DOE");
		assertEquals(expected, transform(collection));
	}

}
