package victor.training.java8.stream.transaction;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.junit.Test;


public class TransactionPlay {
	
	



	private Trader raoul = new Trader("Raoul", "Cambridge");
	private Trader mario = new Trader("Mario","Milan");
	private Trader alan = new Trader("Alan","Cambridge");
	private Trader brian = new Trader("Brian","Cambridge");

	private List<Transaction> transactions = Arrays.asList(
	    new Transaction(brian, 2011, 300),
	    new Transaction(raoul, 2012, 1000),
	    new Transaction(raoul, 2011, 400),
	    new Transaction(mario, 2012, 710),
	    new Transaction(mario, 2012, 700),
	    new Transaction(alan, 2012, 950)
	);
	
	private Transaction[] tx = transactions.toArray(new Transaction[0]);
		
	@Test //1
	public void all_2011_transactions_sorted_by_value() {
		List<Transaction> expected = Arrays.asList(tx[0], tx[2]);
		
		// SOLUTION(
		List<Transaction> list = transactions.stream()	         		
			.filter(t -> t.getYear() == 2011)
			.sorted(Comparator.comparing(Transaction::getValue))
			.collect(toList()); 
		
		assertEquals(expected, list); 									
		// SOLUTION)
	}
		
	@Test //2
	public void unique_cities_of_the_traders() {
		List<String> expected = Arrays.asList("Cambridge", "Milan");
		
		// SOLUTION(
		List<String> list = transactions.stream()             			
			.map(Transaction::getTrader)
			.map(Trader::getCity)
			.distinct()
			.collect(toList());											

		assertEquals(expected, list); 									
		// SOLUTION)
	}
	
	@Test //3
	public void traders_from_Cabridge_sorted_by_name() {
		List<Trader> expected = Arrays.asList(alan, brian, raoul);

		// SOLUTION(
		List<Trader> list = transactions.stream()         				
			.map(Transaction::getTrader)
			.filter(trader -> "Cambridge".equals(trader.getCity()))
			.sorted(Comparator.comparing(Trader::getName))
			.distinct()
			.collect(toList()); 										
		
		assertEquals(expected, list);
		// SOLUTION)
	}
	
	@Test //4
	public void names_of_all_traders_sorted_joined() {
		String expected = "Alan,Brian,Mario,Raoul";
		
		// SOLUTION(
		String joined = transactions.stream()       					
			.map(Transaction::getTrader)
			.map(Trader::getName)
			.sorted()
			.distinct()
			.collect(joining(","));										
		
		assertEquals(expected, joined);
		// 	SOLUTION)
	}
			
	@Test //5
	public void are_traders_in_Milano() {
		// SOLUTION(
		boolean areTradersInMilan = transactions.stream()					
			.map(Transaction::getTrader)
			.anyMatch(trader -> "Milan".equals(trader.getCity()));
		
		assertTrue(areTradersInMilan);
		// SOLUTION)
	}
	
	@Test //6 
	public void sum_of_values_of_transactions_from_Cambridge_traders() { 
		//int sum = -1; // INITIAL
		// SOLUTION(
		int sum = transactions.stream()									
			.filter(t-> "Cambridge".equals(t.getTrader().getCity()))
			.mapToInt(Transaction::getValue) //  returns IntStream
			.sum();
		// SOLUTION)
		
		assertEquals(2650, sum);
	}
	
	@Test //7
	public void max_transaction_value() {
		//int max = -1; // INITIAL
		// SOLUTION(
		int max = transactions.stream()									
			.max(Comparator.comparing(Transaction::getValue))
			.get()
			.getValue();
		// SOLUTION)
		
		assertEquals(1000, max);
	}

	
	@Test
	public void transaction_with_smallest_value() {
		Transaction expected = tx[0];
		// SOLUTION(
		Transaction min = transactions.stream() 						
				.min(Comparator.comparing(Transaction::getValue))
				.get();
		assertEquals(expected, min);
		// SOLUTION)
	}
	@Test
	public void fibonacci_first_10() {
		List<Integer> expected = Arrays.asList(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
		// SOLUTION(
		Stream<Integer> fibonacci = Stream.iterate(						
				new int[]{1,1}, 
				pair -> new int[] {pair[1], pair[0]+pair[1]})
				.map(pair -> pair[0]);
		
		List<Integer> fib10 = fibonacci.limit(10).collect(toList());
		assertEquals(expected, fib10);
		// SOLUTION)
	}
	
	@Test
	public void a_transaction_from_2012() {
		Transaction expected = tx[1];
		// SOLUTION(
		Transaction tx2012 = transactions.stream()
			.filter(transaction -> transaction.getYear() == 2012)
			.findFirst()
			.get();
		
		assertEquals(expected, tx2012);
		// SOLUTION)
	}
	
	@Test
	public void uniqueCharactersOfManyWords() {
		List<String> expected = Arrays.asList("a", "b", "c", "d", "f");
		List<String> wordsStream = Arrays.asList("abcd", "acdf");
		
		// SOLUTION(
		List<String> actual = wordsStream.stream()
				.flatMap(word -> Arrays.asList(word.split("")).stream())
				.distinct()
				.sorted()
				.collect(toList());
		assertEquals(expected, actual);
		// SOLUTION)
	}
	
	
	
	@Test
	public void advanced_sum_using_consumer() {
		List<Integer> numere = Arrays.asList(1, 3, 6, 8,9);
		// SOLUTION(
		MySmartConsumerIBM summer = new MySmartConsumerIBM();
		
		numere.stream().forEach(summer);
		System.out.println("Suma = " +summer.getSum());
		
		int sum = 0;
		for (int n : numere) {
			sum += n;
		}
		// SOLUTION)
	}
	
	// SOLUTION(
	private static class MySmartConsumerIBM implements Consumer<Integer> {
		int sum = 0;

		@Override
		public void accept(Integer n) {
			sum += n;
		}
		
		public int getSum() {
			return sum;
		}
	}
	// SOLUTION)
}
