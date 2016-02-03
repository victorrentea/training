package victor.training.java8.streams;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/*
Filter collection so that only elements with less then 4 characters are returned.
 */
public class FilterCollectionSpec {

	public List<String> transform7(List<String> collection) {
		List<String> newCollection = new ArrayList<>();
		for (String element : collection) {
			if (element.length() < 4) {
				newCollection.add(element);
			}
		}
		return newCollection;
	}

	public List<String> transform(List<String> collection) {
		return collection.stream() // Convert collection to Stream
				.filter(value -> value.length() < 4) // Filter elements with length smaller than 4 characters
				.collect(toList()); // Collect results to a new list
	}

	@Test
	public void transformShouldFilterCollection() {
		List<String> collection = asList("My", "name", "is", "John", "Doe");
		List<String> expected = asList("My", "is", "Doe");
		assertEquals(expected, transform(collection));
	}

}
