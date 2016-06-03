package victor.perf.leaks;

import java.util.HashMap;
import java.util.Map;

public class CachingMethodObject {
	public class UserRightsCalculator {
		public void doStuff() {
			System.out.println("Intelligent Code");
		}
	}
	
	private Map<String, BigObject20MB> cache = new HashMap<>();
	
	public UserRightsCalculator createRightsCalculator() {
		cache.put("a", new BigObject20MB());
		cache.put("b", new BigObject20MB());
		
		return new UserRightsCalculator();
	}
}
