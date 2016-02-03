package victor.training.java8.streams;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.groupingBy;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/*
Group people by nationality
 */
public class GroupingSpec {

	public Map<String, List<Person>> groupByNationality7(List<Person> people) {
		Map<String, List<Person>> map = new HashMap<>();
		for (Person person : people) {
			if (!map.containsKey(person.getNationality())) {
				map.put(person.getNationality(), new ArrayList<>());
			}
			map.get(person.getNationality()).add(person);
		}
		return map;
	}

	public Map<String, List<Person>> groupByNationality(List<Person> people) {
		return people.stream() // Convert collection to Stream
				.collect(groupingBy(Person::getNationality)); // Group people by nationality
	}

	@Test
	public void partitionAdultsShouldSeparateKidsFromAdults() {
		Person sara = new Person("Sara", 4, "Norwegian");
		Person viktor = new Person("Viktor", 40, "Serbian");
		Person eva = new Person("Eva", 42, "Norwegian");
		List<Person> collection = asList(sara, eva, viktor);
		Map<String, List<Person>> result = groupByNationality(collection);
		assertEquals(asList(sara, eva), result.get("Norwegian"));
		assertEquals(asList(viktor), result.get("Serbian"));
	}

}
