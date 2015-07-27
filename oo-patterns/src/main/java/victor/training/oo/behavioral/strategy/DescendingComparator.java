package victor.training.oo.behavioral.strategy;

import java.util.Comparator;

//public class DescendingComparator { // INITIAL
public class DescendingComparator implements Comparator<Integer> { // SOLUTION(

	public int compare(Integer o1, Integer o2) {
		return o2 - o1;
	}

	// SOLUTION)
}
