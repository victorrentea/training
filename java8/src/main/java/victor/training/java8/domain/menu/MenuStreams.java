package victor.training.java8.domain.menu;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.swing.JButton;

import victor.training.java8.domain.menu.Dish.Type;

@SuppressWarnings("unused")
public class MenuStreams {
	
@SuppressWarnings("null")
public static void main(String[] args) {
	List<Dish> menu = Dish.menu;
	
	
	List<Apple> apples2=null;
	
	apples2.sort((o1, o2) -> o1.getWeight() - o2.getWeight());
	
	apples2.sort(Comparator.comparing(Apple::getWeight));
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	List<Dish> vegetarianMenu = menu.stream()
									.filter(Dish::isVegetarian)
									.collect(toList());
	
	menu.stream()
		.filter(dish -> dish.getCalories() < 400)
		.collect(toList());
	
	
	menu.stream()	
		.sorted(Comparator.comparing(Dish::getCalories))
		.skip(1)
		.limit(2)
		.collect(toList());
	
//	List<Integer> dishNamesLengths = menu.stream()
//										.map(Dish::getName) // Stream<String>
//										.map(String::length) // Stream<Integer>
//										.collect(toList());
	
	List<Integer> dishNamesLengths = menu.stream()
			.map(Dish::getName) // Stream<String> 	
			.map(String::length) // Stream<Integer>
			.collect(toList());
	
	List<Dish> dishes = menu.stream()
			.filter(d -> d.getType() == Dish.Type.MEAT)
			.limit(2)
			.collect(toList());
	
	Predicate<Apple> isRed = apple -> apple.getColor() == Color.RED;
	Predicate<Apple> isHeavy = apple -> apple.getWeight() > 150;
	Predicate<Apple> isRedAndHeavy = isRed.and(isHeavy);
	
	List<Apple> apples;
	
	Function<Integer, Integer> f = x -> x * x;
	Function<Integer, Integer> g = x -> x + 1;
	Function<Integer, Integer> h = f.andThen(g);
	int five = h.apply(2); // g(f(2)) = g(2*2) = g(4) = 5
	int nine = g.andThen(f).apply(2); // f(g(2)) = f(2 + 1) = f(3) = 9
	
	List<Integer> list = null;
	
	Optional<Integer> max = list.stream().reduce((i1,i2) -> Math.max(i1, i2));
	
	List<String> words = Arrays.asList("Hello", "World");
	
	words.stream()
		.map(s -> s.split(""))    // Stream<String[]>
		.distinct()
		.collect(toList());
	
	words.stream()
		.map(s -> Arrays.stream(s.split("")))   
		.distinct()
		.collect(toList());
	
	List<String> uniqueChars = words.stream()
								.map(s -> s.split(""))
								.flatMap(Arrays::stream)      
								.distinct()
								.collect(toList());
	
	words.stream()
		.map(String::chars);
	
	Map<Dish.Type, List<Dish>> dishesByType = 
					menu.stream().collect(groupingBy(Dish::getType));
	
	Map<Type, Map<Object, List<Dish>>> dishesByTypeAndCaloricLevel = menu.stream().collect(
	         groupingBy(Dish::getType, 
        		 groupingBy(dish -> {
	                if (dish.getCalories() <= 400) return CaloricLevel.DIET;
	                else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
	                else return CaloricLevel.FAT;
	         } )));
	
	Map<Dish.Type, Long> typesCount = menu.stream().collect(
            groupingBy(Dish::getType, counting()));
	
	
	
	Map<Dish.Type, Optional<Dish>> mostCaloricByType =
		    menu.stream().collect(
	    		groupingBy(Dish::getType,
              			   maxBy(Comparator.comparingInt(Dish::getCalories))));
	
	Map<Dish.Type, Integer> totalCaloriesByType = menu.stream().collect(
			groupingBy(Dish::getType,
                     summingInt(Dish::getCalories)));
	
	Map<Dish.Type, Set<Dish>> groupedAsSets = menu.stream().collect(
			groupingBy(Dish::getType,toSet()));
	
	Function<String, String> upper = String::toUpperCase;
	Function<String, Boolean> empty = String::isEmpty;
	
	
	Predicate<String> empt = String::isEmpty;
	Predicate<String> pr = s->s.contains("a");
	
	Predicate<String> andpr = empt.and(pr);
	Comparator.comparing(User::getFirstName).thenComparing(Comparator.comparing(User::getLastName));

	List<Integer> numbers = new ArrayList<>();
	Predicate<Integer> isPrime = i -> true;
	
	List<Integer> primes = numbers.parallelStream()
		.filter(isPrime)
		.collect(toList());
	
	
	
	JButton button = new JButton();
	
	button.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Hello world!");			
		}
	});
	
	button.addActionListener(e -> {System.out.println("Hello Lambdas!");});
		
	
	List<User> users = new ArrayList<>();
	
	users.sort(new Comparator<User>() {
		@Override
		public int compare(User u1, User u2) {
			return u1.getFirstName().compareTo(u2.getFirstName());
		}
	});
	
	
	
	
	
	Comparator<User> cmp1= (User u1, User u2) -> u1.getFirstName().compareTo(u2.getFirstName());
	Comparator<User> cmp2= (u1, u2) -> u1.getFirstName().compareTo(u2.getFirstName());
	Comparator<User> cmp3= Comparator.comparing(User::getFirstName); // static interface method 
	
	Function<String, Integer> f1 = (String s) -> {return s.length();};
	Function<String, Integer> f2 = (String s) -> s.length();
	Function<String, Integer> f3 = s -> s.length();
	Function<String, Integer> f4 = String::length; // method reference
	 
	Predicate<Apple> p1 = 			(Apple a) -> a.getWeight() > 150; // returns boolean
	Predicate<Apple> p2 = 			a -> a.getWeight() > 150; // the same, parameter type inferred
	
	Consumer<Integer> c1 = (Integer i) -> {System.out.println(i);};
	Consumer<Integer> c2 = i -> {System.out.println(i);};
	Consumer<Integer> c3 = System.out::println; // ?! reference to instance method ?!
	 
	BiConsumer<Integer, Integer> bf = (Integer x, Integer y) -> { // code block
		System.out.println("x="+x+", y="+y);
		System.out.println(x+y);
		// no return statement -> returns void
	}; 
	 
	 
//	Supplier<Wine> c1 = () -> new Wine(); 
//	Supplier<Wine> c2 = Wine::new;  // !!??
//	Wine w1 = c2.get(); // = new Wine()
	 
//	Function<Integer, Wine> c3 = year -> new Wine(year); 
//	Function<Integer, Wine> c4 = Wine::new;  // !!??
//	Wine w2 = c4.apply(1990); // = new Wine(1990);
	  
	
//	(Apple a) -> a.getWeight()	Apple::getWeight
//	() -> Thread.currentThread().dumpStack()	Thread.currentThread()::dumpStack
//	(str, i) -> str.substring(i)	String::substring
//	(String s) -> System.out.println(s)	System.out::println
	
	Function<String, String> splitf = (String s) -> s.split(" ")[1];
	Function<String, Integer> parsef = Integer::parseInt;
	Function<Integer, Wine> newf = Wine::new;
//	Function<String, Wine> f = splitf.andThen(parsef).andThen(newf);
//	Wine w = f.apply("chardonnay 1992");
	
	
	Supplier<Long> l = System::currentTimeMillis;
	Runnable r = Thread::dumpStack;
	
	button.addActionListener(e->{});
	olderThanYear.apply(1990);
}

	static Function<Integer, Predicate<Wine>> olderThanYear = year -> wine -> wine.getYear() <= year;

static class Wine {
	private int year;
	public Wine() {
	}
	public Wine(int year) {
		this.year = year;
	}
	public int getYear() {
		return year;
	}
}



class User {
	private String firstName,lastName;

	public final String getFirstName() {
		return firstName;
	}

	public final void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public final String getLastName() {
		return lastName;
	}

	public final void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
}
public enum CaloricLevel { DIET, NORMAL, FAT }

}
