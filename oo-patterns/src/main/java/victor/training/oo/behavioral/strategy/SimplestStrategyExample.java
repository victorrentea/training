package victor.training.oo.behavioral.strategy;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SimplestStrategyExample {
	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(5, 3, 2, 4, 1, 6);

		System.out.println("Initial List: " + list);
		Comparator<Integer> compareAlgorithm = new AscendingComparator();
		Collections.sort(list, compareAlgorithm);
		System.out.println("List: " + list);
	}
	
}

class AscendingComparator implements Comparator<Integer> {
	@Override
	public int compare(Integer o1, Integer o2) {
		return o1 - o2;
	}
}

//public class DescendingComparator { // INITIAL
class DescendingComparator implements Comparator<Integer> { // SOLUTION(
	public int compare(Integer o1, Integer o2) {
		return o2 - o1;
	} // SOLUTION)
}
