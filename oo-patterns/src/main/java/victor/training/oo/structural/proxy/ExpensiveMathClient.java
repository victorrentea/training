package victor.training.oo.structural.proxy;

import java.math.BigDecimal;

public class ExpensiveMathClient {
	
//	private final ExpensiveMath math; // INITIAL
	private final IExpensiveMath math; // SOLUTION
	
//	public ExpensiveMathClient(ExpensiveMath math) { // INITIAL
	public ExpensiveMathClient(IExpensiveMath math) { // SOLUTION
		this.math = math;
	}
	
	public void doProductionStuff() {
		System.out.println("2 e prim ? " + math.isPrime(new BigDecimal("2")));
		System.out.println("17 e prim ? " + math.isPrime(new BigDecimal("17")));
		System.out.println("10000169  e prim ? " + math.isPrime(new BigDecimal("10000169")));
		System.out.println("10000169  e prim ? " + math.isPrime(new BigDecimal("10000169")));
		
		System.out.println("sqrt(123123123123123123123) = " + math.sqrt(new BigDecimal("123123123123123123123"), 1));
		System.out.println("sqrt(123123123123123123123) (bis) = " + math.sqrt(new BigDecimal("123123123123123123123"), 1));
	}
	
	
	public static void main(String[] args) {
		ExpensiveMath math = new ExpensiveMath();
//		IExpensiveMath math = new ExpensiveMathCachingProxy(math); // SOLUTION
//		IExpensiveMath math = GenericCachingProxy.makeProxyFor(math, IExpensiveMath.class); // SOLUTION
		ExpensiveMathClient client = new ExpensiveMathClient(math);
		client.doProductionStuff();
	}
	

	
	
	
	
	
	
	// Tip: any decent container (Spring,EJB,CDI) can also proxy concrete classes - no need for useless interfaces
	
	// Tip: This Aspect-Oriented Programming principle is used to implement other recurring tasks: 
	// 		Transactions, Security Access Checks, Auditing, Logging 
}
