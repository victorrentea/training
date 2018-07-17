package victor.training.java8.stream.menu;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import victor.training.java8.stream.menu.MenuStreams.Wine;

public class HappyDemo {

	
	public static void main(String[] args) {
//		List<User> users = null;
//		
//		users.sort(Comparator.comparing(User::getLastName)
//		           .thenComparing(Comparator.comparing(User::getFirstName)));
		
		
		String s = "Chardonnay 1980";
		
		Function<String, Wine> f = str -> new Wine(Integer.parseInt(str.split(" ")[1]));
		
		Function<String, String> extractYear = str -> str.split(" ")[1];
		Function<String, Integer> parseStr = Integer::parseInt;
		Function<Integer, Wine> createWine = Wine::new;
		Function<String, Wine> f2 = extractYear.andThen(parseStr).andThen(createWine);
		
		System.out.println(f2.apply(s));
		
		List<Wine> wines = new ArrayList<>();
		wines.add(new Wine(1980));
		
		List<Integer> numbers = Arrays.asList(1,3,6,8,4,7,2);
		List<Integer> numbers2 = Arrays.asList(1,2,3);
		
		
		numbers.stream()
			.map(n -> {
				List<int[]> pairs = new ArrayList<>();
				for(int n2:numbers2) {
					int[] pair = new int[]{n, n2};
					pairs.add(pair);
				}
				return pairs;
			})
			.flatMap(List::stream)
			.collect(toList())
			.stream()
			.map(Arrays::toString)
			.forEach(System.out::println);
		
		
		
		
		
		
		
		Integer produs = numbers.stream()
			.reduce(1, (a,b) -> a*b);
		System.out.println(produs);
			
		
		
		double average = numbers.stream().mapToInt(i->i).average().getAsDouble();
		
		double variance = numbers.stream()
			.map(x -> x-average)
			.map(i -> i * i)
			.mapToDouble(i->i)
			.sum();
		
		
		Optional<Integer> max = numbers.stream().reduce(Integer::max);
		
		
		
		
		List<Integer> squares = numbers.stream()
				.filter(n -> n%2==1)
				.map(n -> n*n)
				.collect(toList());
		System.out.println(squares);
		
		
				
	}
}
