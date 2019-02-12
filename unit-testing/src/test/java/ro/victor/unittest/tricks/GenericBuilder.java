package ro.victor.unittest.tricks;
// FROM https://stackoverflow.com/questions/31754786/how-to-implement-the-builder-pattern-in-java-8
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class GenericBuilder<T> {

	private final Supplier<T> instantiator;

	private List<Consumer<T>> instanceModifiers = new ArrayList<>();

	public GenericBuilder(Supplier<T> instantiator) {
		this.instantiator = instantiator;
	}

	public static <T> GenericBuilder<T> of(Supplier<T> instantiator) {
		return new GenericBuilder<T>(instantiator);
	}
	
	public static <T> GenericBuilder<T> of(T instance) {
		return new GenericBuilder<T>(() -> instance);
	}

	public <U> GenericBuilder<T> with(BiConsumer<T, U> consumer, U value) {
		Consumer<T> modifier = instance -> consumer.accept(instance, value);
		instanceModifiers.add(modifier);
		return this;
	}
	
	public <U> GenericBuilder<T> add(Function<T, Collection<U>> collectionGetter, U newElement) {
		Consumer<T> modifier = instance -> collectionGetter.apply(instance).add(newElement);
		instanceModifiers.add(modifier);
		return this;
	}

	public T build() {
		T value = instantiator.get();
		instanceModifiers.forEach(modifier -> modifier.accept(value));
		instanceModifiers.clear();
		return value;
	}
}

class Person {

	private String name;
	private int age;
	private List<String> phones = new ArrayList<>();
	

	public List<String> getPhones() {
		return phones;
	}

	public void setPhones(List<String> phones) {
		this.phones = phones;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	
}

class CuParamDeCtr {
	private final int date;

	private CuParamDeCtr(int date) {
		this.date = date;
	}
	
}

class Main {
	public static void main(String[] args) {
		
		Date d1 = new Date(); // f(): date
		Supplier<Date> supp = Date::new;
		d1 = supp.get();
		
		Date d2 = new Date(2l); // f(long): date
		Function<Long, Date> supp2 = Date::new;
		d2= supp2.apply(2L);
		
		BiConsumer<Person, String> oRefLaUnSetter = Person::setName;
		
		
		GenericBuilder<Person> personBuilder = GenericBuilder.of(Person::new)
				.with(oRefLaUnSetter, "Otto")
				.add(Person::getPhones, "07200000000")
				.with(Person::setAge, 5);
		Person person = personBuilder.build();
		
		System.out.println(personBuilder.build() == personBuilder.build());
		
		Person builderOnAnExistingIntance = GenericBuilder.of(person).build();
		System.out.println(builderOnAnExistingIntance == person);
		
//		CuParamDeCtr x = GenericBuilder.of(CuParamDeCtr::new).build();
//		CuParamDeCtr x = GenericBuilder.of(() -> new CuParamDeCtr(1)).build();
				
		oRefLaUnSetter.accept(person, "noulNume");
		
		Consumer<String> setterAlUneiInstante = person::setName;
		setterAlUneiInstante.accept("noulNume.Instanta deja o am");
				
		System.out.println(person.getPhones());
	}
}