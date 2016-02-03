package victor.training.java8.streams;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.IntSummaryStatistics;
import java.util.List;

import org.junit.Test;

/*
Get people statistics: average age, count, maximum age, minimum age and sum og all ages.
 */
public class PeopleStatsSpec {

	public Stats getStats7(List<Person> people) {
		long sum = 0;
		int min = people.get(0).getAge();
		int max = 0;
		for (Person person : people) {
			int age = person.getAge();
			sum += age;
			min = Math.min(min, age);
			max = Math.max(max, age);
		}
		return new Stats(people.size(), sum, min, max);
	}

	public IntSummaryStatistics getStats(List<Person> people) {
		return people.stream() //
				.mapToInt(Person::getAge) //
				.summaryStatistics(); //
	}


	@Test
	public void getStatsShouldReturnCorrectData() {
		Person sara = new Person("Sara", 4);
		Person viktor = new Person("Viktor", 40);
		Person eva = new Person("Eva", 42);
		List<Person> collection = asList(sara, eva, viktor);
		assertEquals((double) (4 + 40 + 42) / 3, getStats(collection).getAverage(),.0001);
		assertEquals(3, getStats(collection).getCount());
		assertEquals(42, getStats(collection).getMax());
		assertEquals(4, getStats(collection).getMin());
		assertEquals(40 + 42 + 4, getStats(collection).getSum());
	}

}
