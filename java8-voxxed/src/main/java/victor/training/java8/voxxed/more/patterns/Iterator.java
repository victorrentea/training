package victor.training.java8.voxxed.more.patterns;

import java.util.Arrays;
import java.util.List;

public class Iterator {

	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(1,2,3,4);
		
		// external iteration
		for (Integer i : list) {
			System.out.println(i);
		}
		
		// internal iteration
		list.stream().forEach(i -> {System.out.println(i);});
		list.stream().forEach(System.out::println);
	}
}
