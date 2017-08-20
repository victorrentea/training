package victor.training.oo.structural.proxy;

import static java.util.Arrays.asList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//public class ExpensiveMathCachingProxy { // INITIAL
//	private Map<List<?>, Object> cache = new HashMap<>(); // INITIAL 
public class ExpensiveMathCachingProxy implements IExpensiveMath { // SOLUTION (

	private final IExpensiveMath delegate;
// Note: Silly cache implementation. Such code might produce OutOfMemoryErrors.
	private Map<List<?>, Object> cache = new HashMap<>(); 
	
	public ExpensiveMathCachingProxy(IExpensiveMath delegate) {
		this.delegate = delegate;
	}

	public BigDecimal sqrt(BigDecimal A, int SCALE) {
		return (BigDecimal) cache.computeIfAbsent(getCacheKey("sqrt", A, SCALE), k -> delegate.sqrt(A, SCALE));
	}

	public boolean isPrime(BigDecimal number) {
		return (boolean) cache.computeIfAbsent(getCacheKey("isPrime", number), k -> delegate.isPrime(number));
	}
	
	// SOLUTION )

	private List<Object> getCacheKey(String methodName, Object... args) {
		List<Object> list = new ArrayList<>();
		list.add(methodName);
		list.addAll(asList(args));
		return list;
	}
	
	
	public static void main(String[] args) {
		System.out.println("Tip: Lists in Java implement hashCode() and equals() based on their elements!");
		List<Object> list1 = asList(1, "a");
		System.out.printf("%s.hashCode = %d\n",list1, list1.hashCode());
		
		List<Object> list2 = asList(1, "a");
		System.out.printf("%s.hashCode = %d\n",list2, list2.hashCode());
		
		System.out.printf("%s.equals(%s) = %s\n", list1, list2, list1.equals(list2));
	}
	
}
