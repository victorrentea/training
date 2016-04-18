package victor.training.java8.domain.menu;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ABSPlay {
	
	
	public static void main(String[] args) {
		List<Apple> apples = null;
		
		
		Collections.sort(apples, Comparator.comparing(Apple::getWeight).reversed());
		
	}
	

}
