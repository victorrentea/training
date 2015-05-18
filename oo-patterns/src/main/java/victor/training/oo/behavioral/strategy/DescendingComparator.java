package victor.training.oo.behavioral.strategy;

import java.util.Comparator;

public class DescendingComparator implements Comparator<Integer> {

	@Override
	public int compare(Integer o1, Integer o2) {
		return o2 - o1;
	}

}
