package victor.training.java8.paral;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toConcurrentMap;
import static java.util.stream.Collectors.toList;

import java.util.List;

public class SerialSteps {

	public static void main(String[] args) {
		List<Integer> list = asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		
//		List<Integer> list2 = 
				list.stream()
			.parallel()
			.map(i -> {
				ThreadUtils.println("Half "+ i);
				return i/ 2;
			})
//			.distinct()
			.sorted((a,b) -> {
				ThreadUtils.println("sorting");
				return a -b;
			})
			.map(i -> {
				ThreadUtils.println("Square "+ i);
				return i * i;
			})
//			.collect(toList())
//			.collect(toConcurrentMap(i->i, i->i))
			.forEachOrdered(n -> ThreadUtils.println("out "+ n));
			;
//		System.out.println(list2);
		// TODO .reduce runs in parallel ?
	 
	}
}
