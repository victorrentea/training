package victor.training.java8.streams;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

/*
Get names of all kids (under age of 18)
 */
public class KidsSpec {

	public Set<String> getKidNames7(List<Person> people) {
		Set<String> kids = new HashSet<>();
		for (Person person : people) {
			if (person.getAge() < 18) {
				kids.add(person.getName());
			}
		}
		return kids;
	}

	public Set<String> getKidNames(List<Person> people) {
		return people.stream() // make stream
				.filter(person -> person.getAge() < 18) // Filter kids (under age of 18)
				.map(Person::getName) // Map Person elements to names
				.collect(toSet()); // Collect values to a Set
	}

	@Test
	public void getKidNameShouldReturnNamesOfAllKidsFromNorway() {
		Person sara = new Person("Sara", 4);
		Person viktor = new Person("Viktor", 40);
		Person eva = new Person("Eva", 42);
		Person anna = new Person("Anna", 5);
		List<Person> collection = asList(sara, eva, viktor, anna);
		assertEquals(new HashSet<>(Arrays.asList("Sara", "Anna")), getKidNames(collection));
	}

}
