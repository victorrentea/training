package victor.training.java8.streams;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.Comparator;
import java.util.List;

import org.junit.Test;

/*
Get oldest person from the collection
 */
public class OldestPersonSpec {
	
	public Person getOldestPerson7(List<Person> people) {
        Person oldestPerson = new Person("", 0);
        for (Person person : people) {
            if (person.getAge() > oldestPerson.getAge()) {
                oldestPerson = person;
            }
        }
        return oldestPerson;
    }

    public Person getOldestPerson(List<Person> people) {
        return people.stream() // Convert collection to Stream
                .max(Comparator.comparing(Person::getAge)) // Compares people ages
                .get(); // Gets stream result
    }


    @Test
    public void getOldestPersonShouldReturnOldestPerson() {
        Person sara = new Person("Sara", 4);
        Person viktor = new Person("Viktor", 40);
        Person eva = new Person("Eva", 42);
        List<Person> collection = asList(sara, eva, viktor);
        assertEquals(eva, getOldestPerson(collection));
    }

}
