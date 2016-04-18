package victor.training.java8.domain.transaction;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class TransactionPlay {
	
	class User {
		int age;

		public User(int age) {
			this.age = age;
		}

		public final int getAge() {
			return age;
		}

		public final void setAge(int age) {
			this.age = age;
		}

		public User() {
			super();
			// TODO Auto-generated constructor stub
		}
		
	}
	public static void main(String[] args) {
		
		
		Trader raoul = new Trader("Raoul", "Cambridge");
		Trader mario = new Trader("Mario","Milan");
		Trader alan = new Trader("Alan","Cambridge");
		Trader brian = new Trader("Brian","Cambridge");
	
		List<Transaction> transactions = Arrays.asList(
		    new Transaction(brian, 2011, 300),
		    new Transaction(raoul, 2012, 1000),
		    new Transaction(raoul, 2011, 400),
		    new Transaction(mario, 2012, 710),
		    new Transaction(mario, 2012, 700),
		    new Transaction(alan, 2012, 950)
		);
		
		
		// 1
		transactions.stream()	
			.filter(t -> t.getYear() == 2011)
			.sorted(Comparator.comparing(Transaction::getValue))
			.collect(toList());
		
		// 2
		transactions.stream()
			.map(Transaction::getTrader)
			.map(Trader::getCity)
			.distinct()
			.collect(toList());
	
		// 3
		transactions.stream()
			.map(Transaction::getTrader)
			.filter(trader -> "Cambridge".equals(trader.getCity()))
			.sorted(Comparator.comparing(Trader::getName))
			.collect(toList());
		
		// 4
		String joined = transactions.stream()
			.map(Transaction::getTrader)
			.map(Trader::getName)
			.sorted()
			.distinct()
			.collect(joining(":"));
		System.out.println(joined);
			
		// 5
		boolean b = transactions.stream()
			.map(Transaction::getTrader)
			.anyMatch(trader -> "Milano".equals(trader.getCity()));
		
		// 6
		transactions.stream()
			.filter(t-> "Cambridge".equals(t.getTrader().getCity()))
			.mapToInt(Transaction::getValue) //  returns IntStream
			.sum();
		
		
		// 7
		int ttt = transactions.stream()
			.max(Comparator.comparing(Transaction::getValue))
			.get()
			.getValue();
		
//		int val = 1_000_000;
		
		Stream<Integer> fibonacci = Stream.iterate(
				new int[]{1,1}, 
				pair -> new int[]{pair[1], pair[0]+pair[1]})
				.map(pair -> pair[0]);
		
		fibonacci.limit(10).forEach(System.out::println);
	}
}
