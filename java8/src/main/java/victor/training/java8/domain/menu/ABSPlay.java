package victor.training.java8.domain.menu;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import victor.training.java8.domain.menu.MenuStreams.Wine;

public class ABSPlay {
	
	
	public static void main(String[] args) {
		List<Apple> apples = null;
		
		
//		Collections.sort(apples, Comparator.comparing(Apple::getWeight).reversed());
//		
//		
//		List<User> users = null;
//		
//		Collections.sort(users, Comparator.comparing(User::getLastName)
//				.thenComparing(Comparator.comparing(User::getFirstName)));
//
//		Function<String, Wine> f = s -> new Wine(Integer.parseInt(s.split(" ")[1]));
		
		
		Function<String, String> extractYearStr = s -> s.split(" ")[1];
		Function<String, Integer> parseYearStr = Integer::parseInt;
		Function<Integer, Wine> createWine = Wine::new;
		
		Function<String, Wine> f2 = extractYearStr
				.andThen(parseYearStr)
				.andThen(createWine);
		
		System.out.println(f2.apply("Chardonnay 1980"));
//		Wine w = f.apply("Chardonnay 1980");
		
		
//		List<User> users = null;
//		
//		List<String> names = users.stream()
//			.filter(user -> user.getFirstName().contains("a"))
//			.map(User::getFirstName)
//			.collect(toList());
		
		
		List<Integer> numbers = Arrays.asList(1, 3, 5, 8);
		List<Integer> squares = numbers.stream()
				.filter(number -> number % 2 == 1)
				.map(number -> number * number)
				.collect(toList());
		
		System.out.println(squares);
		
		List<Integer> list1 = Arrays.asList(1,2,3);
		List<Integer> list2 = Arrays.asList(4,5);
		
		List<List<Integer>> pairs = list1.stream()
				.flatMap(elementDinLista1 -> {
					return list2.stream()
							.map(elementDinLista2 -> Arrays.asList(elementDinLista1, elementDinLista2));							
				})
				.collect(toList());
		System.out.println(pairs);
		
		List<Apple> apples2 = Arrays.asList(new Apple(150), new Apple(250), new Apple(350));
		// sa calculam daca in lista TOATE merele sunt sub 200 
		boolean doarSub200_1 = apples2.stream() 
				.filter(apple -> {
					System.out.println("Testez un mar");
					return apple.getWeight() < 200;
				})
				.count() == 0;
		System.out.println(doarSub200_1);
		
		boolean doarSub200_2 = apples2.stream() 
				.allMatch(apple -> {
					System.out.println("Testez un mar#2");
					return apple.getWeight() < 200;
				});
		System.out.println(doarSub200_2);
		
		
		List<Integer> nums = Arrays.asList(1,2,3,4,6,7,4,7,3,2,4,6);
		double average = nums.stream() // Stream<Integer>
			.mapToInt(i->i)
			.average()
			.getAsDouble();
		
		Double variance = nums.stream()
				.map(x -> x - average)
				.map(p -> p*p)
				.mapToDouble(d->d)
				.sum();
//				.reduce((double) 0, (a,b) -> a+b);
		
	}
	

}
