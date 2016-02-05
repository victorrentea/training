package victor.training.java8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Java7Impl {

	public static List<String> filterLessThan4Characters(List<String> collection) {
		List<String> newCollection = new ArrayList<>();
		for (String element : collection) {
			if (element.length() < 4) {
				newCollection.add(element);
			}
		}
		return newCollection;
	}

	public static List<String> flattenNestedList(List<List<String>> collection) {
		List<String> newCollection = new ArrayList<>();
		for (List<String> subCollection : collection) {
			for (String value : subCollection) {
				newCollection.add(value);
			}
		}
		return newCollection;
	}

	public static Map<String, List<Person>> groupByNationality(List<Person> people) {
		Map<String, List<Person>> map = new HashMap<>();
		for (Person person : people) {
			if (!map.containsKey(person.getNationality())) {
				map.put(person.getNationality(), new ArrayList<>());
			}
			map.get(person.getNationality()).add(person);
		}
		return map;
	}

	public static String joinPeopleNames(List<Person> people) {
		String result = "Names: ";
		for (Person person : people) {
			result += person.getName()+ ", ";
		}
		if (!people.isEmpty()) {
			result = result.substring(0, result.length()-2);
		}
		return result + ".";
	}

	public static Set<String> getKidNames(List<Person> people) {
		Set<String> kids = new HashSet<>();
		for (Person person : people) {
			if (person.getAge() < 18) {
				kids.add(person.getName());
			}
		}
		return kids;
	}

	public static Person getOldestPerson(List<Person> people) {
		Person oldestPerson = new Person("", 0);
		for (Person person : people) {
			if (person.getAge() > oldestPerson.getAge()) {
				oldestPerson = person;
			}
		}
		return oldestPerson;
	}

	public static Map<Boolean, List<Person>> partitionAdults(List<Person> people) {
		Map<Boolean, List<Person>> map = new HashMap<>();
		map.put(true, new ArrayList<>());
		map.put(false, new ArrayList<>());
		for (Person person : people) {
			map.get(person.getAge() >= 18).add(person);
		}
		return map;
	}

	public static Stats getStats(List<Person> people) {
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

	public static int sumAllElements(List<Integer> numbers) {
		int total = 0;
		for (int number : numbers) {
			total += number;
		}
		return total;
	}
	
	public static List<String> allItemsToUpperCase(List<String> collection) {
		List<String> newCollection = new ArrayList<>();
		for (String element : collection) {
			newCollection.add(element.toUpperCase());
		}
		return newCollection;
	}

	public static double standardDev(List<Integer> numbers) {
		int sumsq = 0;
		for (int v : numbers) {
			sumsq += v * v;
		}
		return Math.sqrt(sumsq) / numbers.size();
	}
}
