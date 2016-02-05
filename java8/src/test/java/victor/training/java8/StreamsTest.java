package victor.training.java8;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import victor.training.java8.Java7Impl;
import victor.training.java8.Person;


public class StreamsTest {

	private Person sara = new Person("Sara", 4, "Norwegian");
	private Person viktor = new Person("Viktor", 40, "Serbian");
	private Person eva = new Person("Eva", 42, "Norwegian");
	private Person anna = new Person("Anna", 5);

	@Test
	/** Filter collection so that only elements with less then 4 characters are returned in a new list. */
	public void filterOnlyUnderLength4() {
		List<String> collection = asList("My", "name", "is", "John", "Doe");
		List<String> expected = asList("My", "is", "Doe");
		
		assertEquals(expected, Java7Impl.filterLessThan4Characters(collection));
		assertEquals(expected, 
				collection.stream() // Convert collection to Stream
				.filter(value -> value.length() < 4) // Filter elements with length smaller than 4 characters
				.collect(toList()));
	}
	
	 @Test
    /** Flatten multidimensional collection */
    public void flattenNestedList() {
        List<List<String>> collection = asList(asList("Viktor", "Farcic"), asList("John", "Doe", "Third"));
        List<String> expected = asList("Viktor", "Farcic", "John", "Doe", "Third");
        
        assertEquals(expected, Java7Impl.flattenNestedList(collection));
        assertEquals(expected, collection.stream() //
        		.flatMap(value -> value.stream()) // Replace list with stream
        		.collect(toList()));
    }
	 
	@Test
	/** Group people by nationality */
	public void groupByNationality() {
		List<Person> collection = asList(sara, eva, viktor);
		
		Map<String, List<Person>> expected = new HashMap<>();
		expected.put("Norwegian", Arrays.asList(sara, eva));
		expected.put("Serbian", Arrays.asList(viktor));
		
		assertEquals(expected, Java7Impl.groupByNationality(collection));
		assertEquals(expected, collection.stream() // 
				.collect(groupingBy(Person::getNationality)));
	}
	
	@Test
	/** Return people names separated by comma */
	public void joinNamesAsString() {
		List<Person> collection = asList(sara, viktor, eva);
		assertEquals("Names: Sara, Viktor, Eva.", Java7Impl.joinPeopleNames(collection));
		assertEquals("Names: Sara, Viktor, Eva.", 
				collection.stream() // Convert collection to Stream
				.map(Person::getName) // Map Person to name
				.collect(joining(", ", "Names: ", ".")));
	}
	
	@Test
	/** Get names of all kids (under age of 18) as a SET */
	public void getKidNames() {
		List<Person> collection = asList(sara, eva, viktor, anna);
		Set<String> expected = new HashSet<>(Arrays.asList("Sara", "Anna"));
		
		assertEquals(expected, Java7Impl.getKidNames(collection));
		assertEquals(expected, 
				collection.stream() // make stream
				.filter(person -> person.getAge() < 18) // Filter kids (under age of 18)
				.map(Person::getName) // Map Person elements to names
				.collect(toSet()));
	}
	
	@Test
	/** Get oldest person from the collection */
    public void getOldestPerson() {
        List<Person> collection = asList(sara, eva, viktor);
        assertEquals(eva, Java7Impl.getOldestPerson(collection));
        assertEquals(eva, 
        		collection.stream() // Convert collection to Stream
        		.max(Comparator.comparing(Person::getAge)) // Compares people ages
        		.get());
    }
	
	@Test
	/** Partition adults and kids */
	public void partitionAdults() {
		List<Person> collection = asList(sara, eva, viktor);
		
		Map<Boolean, List<Person>> expected = new HashMap<>();
		expected.put(true /*adult*/, Arrays.asList(viktor, eva));
		expected.put(false /*NOT adult*/, Arrays.asList(sara));
		
		assertEquals(expected, Java7Impl.partitionAdults(collection));
		assertEquals(expected, collection.stream() // Convert collection to Stream
				.collect(partitioningBy(p -> p.getAge() >= 18)));
	}
	
	
	@Test
	/** Get people statistics: average age, count, maximum age, minimum age and sum og all ages. */
	public void getStats() {
		List<Person> collection = asList(sara, eva, viktor);
//		Stats stats = Java7Impl.getStats(collection);
		IntSummaryStatistics stats = collection.stream() //
				.mapToInt(Person::getAge) //
				.summaryStatistics();
		
		assertEquals((double) (4 + 40 + 42) / 3, stats.getAverage(),.0001);
		assertEquals(3, stats.getCount());
		assertEquals(42, stats.getMax());
		assertEquals(4, stats.getMin());
		assertEquals(40 + 42 + 4, stats.getSum());
	}

    @Test
    /** Sum all elements of a collection */
    public void sumOfElements() {
        List<Integer> numbers = asList(1, 2, 3, 4, 5);
        assertEquals(1 + 2 + 3 + 4 + 5, Java7Impl.sumAllElements(numbers));
        assertEquals(1 + 2 + 3 + 4 + 5, 
        		(int) numbers.stream() // Convert collection to Stream
        		.reduce(0, (total, number) -> total + number));
    }
    
    @Test
	/** Convert elements of a collection to upper case. */
	public void allItemsToUpperCase() {
		List<String> collection = asList("My", "name", "is", "John", "Doe");
		List<String> expected = asList("MY", "NAME", "IS", "JOHN", "DOE");
		assertEquals(expected, Java7Impl.allItemsToUpperCase(collection));
		assertEquals(expected, collection.stream() // Convert collection to Stream
				.map(String::toUpperCase) // Convert each element to upper case
				.collect(toList()));
	}



}
