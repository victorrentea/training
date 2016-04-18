package victor.training.java8.domain.transaction;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
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
		
		
		System.out.println(transactions.stream()
			.filter(t -> t.getYear() == 2011)
			.sorted(Comparator.comparing(Transaction::getValue))
			.collect(toList()));
		
		System.out.println(transactions.stream()
				.map(Transaction::getTrader)
				.anyMatch(t->"Milan".equals(t.getCity())));

		transactions.stream()
			.filter(t->t.getTrader().getCity().equals("Cambridge"))
			.map(Transaction::getValue)
			.collect(toList());
		
		transactions.stream()
			.mapToInt(Transaction::getValue)
			.max();
		
		
//		select angajat
//		from angajati
//		unde angajat.salauri = (select max salariud from )
		
		int minValue = transactions.stream()
				.mapToInt(Transaction::getValue)
				.min()
				.getAsInt();
		
		Transaction tr2 = transactions.stream()
			.filter(tr->tr.getValue() == minValue)
			.findAny()
			.get();
		
		
//		1 1 2 3 5 8 
		
		Stream<Integer> fibonacci = Stream
				.iterate(new int[]{1,1}, (int[] prev) -> new int[]{prev[1], prev[0] + prev[1]})
				.map(pair -> pair[0])
				;
		
		fibonacci.limit(1000)
			.forEach(System.out::println);;
		
		
		int N = 50;
		
		Predicate<Integer> isPrime = x->true;
		
		 Map<Boolean, List<Integer>> result = IntStream.rangeClosed(1, N).boxed()
			.collect(partitioningBy(isPrime));
			
		Predicate<User> under50bis = user -> user.getAge() < 50;
		 
		Function<Integer, Predicate<User>> ageUnder = age -> user -> user.getAge() < age;
		Predicate<User> under50 = ageUnder.apply(50);
		Predicate<User> under40 = ageUnder.apply(40);
		
		System.exit(2);
		
		
		
		
		
		
		
		
		
		
		transactions.stream()
			.filter(t->t.getYear() == 2011)
			.sorted(Comparator.comparing(Transaction::getValue))
			.collect(toList());
		
		System.out.println(transactions.stream()
			.map(Transaction::getTrader)
			.map(Trader::getName)
			.distinct()
			.sorted()
			.collect(joining(",")));
		
		System.out.println(transactions.stream().map(Transaction::getTrader).anyMatch(t -> t.getCity().equals("Milan")));
		
		transactions.stream()
			.filter(t -> t.getTrader().getCity().equals("Cambridge"))
			.map(Transaction::getValue)
			.forEach(System.out::println);
		System.out.println(transactions.stream()
			.mapToInt(Transaction::getValue)
			.max()
			.getAsInt());
	}
}
