package victor.training.java8;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;


public class StreamsTest {

	private Person sara4 = new Person("Sara", 4, "Norwegian");
	private Person viktor40 = new Person("Viktor", 40, "Serbian");
	private Person eva42 = new Person("Eva", 42, "Norwegian");
	private Person anna = new Person("Anna", 5);

	@Test
	/** Filter collection so that only elements with less then 4 characters are returned in a new list. */
	public void filterOnlyUnderLength4() {
		List<String> collection = asList("My", "name", "is", "John", "Doe");
		List<String> expected = asList("My", "is", "Doe");
		
		assertEquals(expected, Java7Impl.filterLessThan4Characters(collection));
		assertEquals(expected, collection.stream() //
				.filter(s -> s.length() < 4) // Filter elements with length smaller than 4 characters
				.collect(Collectors.toList()));
	}
	
	 @Test
    /** Flatten multidimensional collection */
    public void flattenNestedList() {
        List<List<String>> collection = asList(asList("Viktor", "Farcic"), asList("John", "Doe", "Third"));
        List<String> expected = asList("Viktor", "Farcic", "John", "Doe", "Third");

        assertEquals(expected, Java7Impl.flattenNestedList(collection));
        assertEquals(expected, collection.stream() //
        		// elements of first stream are Lists. Convert each of them to Streams
        		.flatMap(stringList -> stringList.stream()) // will concatenate the returned streams
        		.collect(Collectors.toList()));
    }
	 
	@Test
	/** Group people by nationality */
	public void groupByNationality() {
		List<Person> collection = asList(sara4, eva42, viktor40);
		
		Map<String, List<Person>> expected = new HashMap<>();
		expected.put("Norwegian", Arrays.asList(sara4, eva42));
		expected.put("Serbian", Arrays.asList(viktor40));
		
		assertEquals(expected, Java7Impl.groupByNationality(collection));
		assertEquals(expected, collection.stream() //
				.collect(Collectors.groupingBy(person -> person.getNationality())));
		// OR: .collect(groupingBy(Person::getNationality)));
	}
	
	@Test
	/** Return people names separated by comma */
	public void joinNamesAsString() {
		List<Person> collection = asList(sara4, viktor40, eva42);
		assertEquals("Names: Sara, Viktor, Eva.", Java7Impl.joinPeopleNames(collection));
		assertEquals("Names: Sara, Viktor, Eva.", collection.stream() //
				.map(person -> person.getName()) //
				// OR: .map(Person::getName)
				.collect(Collectors.joining(", ", "Names: ", ".")));
	}
	
	@Test
	/** Get names of all kids (under age of 18) as a SET */
	public void getKidNames() {
		List<Person> collection = asList(sara4, eva42, viktor40, anna);
		Set<String> expected = new HashSet<>(Arrays.asList("Sara", "Anna"));
		
		assertEquals(expected, Java7Impl.getKidNames(collection));
		assertEquals(expected, collection.stream() //
				.filter(person -> person.getAge() < 18) // filter kids (under age of 18)
				.map(Person::getName) // map Person to name
				.collect(Collectors.toSet()));
	}
	
	@Test
	/** Get oldest person from the collection */
    public void getOldestPerson() {
        List<Person> collection = asList(sara4, eva42, viktor40);
        assertEquals(eva42, Java7Impl.getOldestPerson(collection));
        assertEquals(eva42, collection.stream() // Convert collection to Stream
        		.max(Comparator.comparing(Person::getAge)) // Compares people ages
       		// OR.max((p1, p2) -> p1.getAge() - p2.getAge()))
        		.get()); // get the value out of the Optional<>
    }
	
	@Test
	/** Partition adults and kids */
	public void partitionAdults() {
		List<Person> collection = asList(sara4, eva42, viktor40);
		
		Map<Boolean, List<Person>> expected = new HashMap<>();
		expected.put(true /*adult*/, Arrays.asList(eva42, viktor40));
		expected.put(false /*NOT adult*/, Arrays.asList(sara4));
		
		assertEquals(expected, Java7Impl.partitionAdults(collection));
		assertEquals(expected, collection.stream() //
				.collect(Collectors.partitioningBy(p -> p.getAge() >= 18)));
	}
	
	
	@Test
	/** Get people statistics: average age, count, maximum age, minimum age and sum og all ages. */
	public void getStats() {
		List<Person> collection = asList(sara4, eva42, viktor40);
//		Stats stats = Java7Impl.getStats(collection);
		IntSummaryStatistics stats = collection.stream() //
				.mapToInt(Person::getAge) //
				.summaryStatistics();
		
		assertEquals((double) (4 + 40 + 42) / 3, stats.getAverage(),.0001);
		assertEquals(3, stats.getCount());
		assertEquals(42, stats.getMax());
		assertEquals(4, stats.getMin());
		assertEquals(40 + 42 + 4, stats.getSum());
//		collection.stream().mapToInt(Person::getAge).summaryStatistics()
		
		
	}

    @Test
    /** Sum all elements of a collection */
    public void sumOfElements() {
        List<Integer> numbers = asList(1, 2, 3, 4, 5);
        int expected = 1 + 2 + 3 + 4 + 5;
        assertEquals(expected, Java7Impl.sumAllElements(numbers));
        assertEquals(expected, (int) numbers.stream() //
        		.collect(Collectors.reducing(0, (total, number) -> total + number)));
    }
    
    @Test
	/** Convert elements of a collection to upper case. */
	public void allItemsToUpperCase() {
		List<String> collection = asList("My", "name", "is", "John", "Doe");
		List<String> expected = asList("MY", "NAME", "IS", "JOHN", "DOE");
		assertEquals(expected, Java7Impl.allItemsToUpperCase(collection));
		assertEquals(expected, collection.stream() //
				.map(String::toUpperCase) // each string -> string.toUpperCase()
				.collect(toList()));
	}
    
    @Test
    /** Standard deviation of a number collection = sqrt(a*a + b*b + ...) / n */
    public void standardDeviation() {
        List<Integer> numbers = asList(6,4,2,7,2,4,6,4,5);
        double expected = Java7Impl.standardDev(numbers);
        assertEquals(expected, Math.sqrt(numbers.stream().mapToInt(v->v*v).sum())/numbers.size(), .000001);
    }
    
    @Test
    public void infiniteFibonacci() {
    	List<Integer> expected = Arrays.asList(1,1,2,3,5,8,13,21,34,55);
    	assertEquals(expected, //
    			Stream.iterate(new int[]{1, 1}, lastTwo -> new int[]{lastTwo[1], lastTwo[0] + lastTwo[1]})
    			.map(pair->pair[0]) // extract the first item in the pairs
    			.limit(10) // pick only the first 10 elements
    			.collect(toList()));
    }
    
    @Test
    // al cincilea cel mai mic element dintr-o lista de 100 de nr generate aleator
    public void fiftSmallestElementFromRandomlyGeneratedList() {
    	Random rand = new Random();
    	Integer min5 = Stream.generate(() -> rand.nextInt(10000))
    			.limit(100)
    			.sorted()
    			.skip(4)
    			.findFirst()
    			.get();
		System.out.println(min5);
    }


}
