package victor.training.java8.streams;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

/*
Return people names separated by comma
 */
public class JoiningSpec {

	public String namesToString7(List<Person> people) {
		String label = "Names: ";
		StringBuilder sb = new StringBuilder(label);
		for (Person person : people) {
			if (sb.length() > label.length()) {
				sb.append(", ");
			}
			sb.append(person.getName());
		}
		sb.append(".");
		return sb.toString();
	}

	public String namesToString(List<Person> people) {
		return people.stream() // Convert collection to Stream
				.map(Person::getName) // Map Person to name
				.collect(joining(", ", "Names: ", ".")); // Join names
	}

	@Test
	public void toStringShouldReturnPeopleNamesSeparatedByComma() {
		Person sara = new Person("Sara", 4);
		Person viktor = new Person("Viktor", 40);
		Person eva = new Person("Eva", 42);
		List<Person> collection = asList(sara, viktor, eva);
		assertEquals("Names: Sara, Viktor, Eva.", namesToString(collection));
	}

}
