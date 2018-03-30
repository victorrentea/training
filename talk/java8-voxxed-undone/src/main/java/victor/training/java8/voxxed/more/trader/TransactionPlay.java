package victor.training.java8.voxxed.more.trader;

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
		
	}
		
	@Test //2
	public void unique_cities_of_the_traders() {
		List<String> expected = Arrays.asList("Cambridge", "Milan");
		
	}
	
	@Test //3
	public void traders_from_Cabridge_sorted_by_name() {
		List<Trader> expected = Arrays.asList(alan, brian, raoul);

	}
	
	@Test //4
	public void names_of_all_traders_sorted_joined() {
		String expected = "Alan,Brian,Mario,Raoul";
		
	}
	
	@Test //6 
	public void sum_of_values_of_transactions_from_Cambridge_traders() { 
		int sum = -1; 
		
		assertEquals(2650, sum);
	}
	
	@Test //7
	public void max_transaction_value() {
		int max = -1; 
		
		assertEquals(1000, max);
	}

	
	@Test
	public void transaction_with_smallest_value() {
		Transaction expected = tx[0];
	}
	@Test
	public void fibonacci_first_10() {
		List<Integer> expected = Arrays.asList(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
	}
	
	@Test
	public void a_transaction_from_2012() {
		Transaction expected = tx[1];
	}
	
	@Test
	public void uniqueCharactersOfManyWords() {
		List<String> expected = Arrays.asList("a", "b", "c", "d", "f");
		List<String> wordsStream = Arrays.asList("abcd", "acdf");
		
	}
	
	
	
	@Test
	public void advanced_sum_using_consumer() {
		List<Integer> numere = Arrays.asList(1, 3, 6, 8,9);
	}
	
}
