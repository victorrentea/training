package victor.training.java8.streams;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.partitioningBy;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/*
Partition adults and kids
 */
public class PartitioningSpec {

	public Map<Boolean, List<Person>> partitionAdults7(List<Person> people) {
		Map<Boolean, List<Person>> map = new HashMap<>();
		map.put(true, new ArrayList<>());
		map.put(false, new ArrayList<>());
		for (Person person : people) {
			map.get(person.getAge() >= 18).add(person);
		}
		return map;
	}

	public Map<Boolean, List<Person>> partitionAdults(List<Person> people) {
		return people.stream() // Convert collection to Stream
				.collect(partitioningBy(p -> p.getAge() >= 18)); // Partition stream of people into adults (age => 18)
																	// and kids
	}

	@Test
	public void partitionAdultsShouldSeparateKidsFromAdults() {
		Person sara = new Person("Sara", 4);
		Person viktor = new Person("Viktor", 40);
		Person eva = new Person("Eva", 42);
		List<Person> collection = asList(sara, eva, viktor);
		Map<Boolean, List<Person>> result = partitionAdults(collection);
		
		assertTrue(result.get(true).contains(viktor));
		assertTrue(result.get(true).contains(eva));
		assertTrue(result.get(false).contains(sara));
	}

}
