package junkomat;

import static java.util.Comparator.comparing;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class Coins {
	private static final List<Coin> COINS_DESCENDING = 
			Stream.of(Coin.values()).sorted(comparing(Coin::getCents).reversed()).collect(toList());
	
	private final Map<Coin, Integer> values= new HashMap<>(Stream.of(Coin.values()).collect(toMap(identity(), x -> 0)));

	public Coins(Coin...coins) {
		for (Coin coin : coins) {
			values.put(coin, values.get(coin) + 1);
		}
	}
	
	public int sumTotalCents() {
		return values.entrySet().stream().mapToInt(e -> e.getValue() * e.getKey().cents).sum();
	}

	public Coins(Map<Coin, Integer> values) {
		this.values.putAll(values);
	}
	
	
	public Coins add(Coins toAdd) {
		Map<Coin, Integer> newValues = new HashMap<>(this.values);
		for (Coin coin: Coin.values()) {
			newValues.put(coin, newValues.get(coin) + toAdd.get(coin));
		}
		return new Coins(newValues);
	}

	public Coins add(Coin coin) {
		Map<Coin, Integer> newValues = new HashMap<>(this.values);
		newValues.put(coin, newValues.get(coin) + 1);
		return new Coins(newValues);
	}

	public Coins substract(Coins coinsToSubstract) {
		Map<Coin, Integer> newValues = new HashMap<>(this.values);
		for (Coin coin: Coin.values()) {
			newValues.put(coin, newValues.get(coin) - coinsToSubstract.get(coin));
		}
		return new Coins(newValues);
	}

	public Integer get(Coin coin) {
		return values.get(coin);
	}
	
	public Optional<Coins> computeChange(int cents) {
		Coins returnedCoins = new Coins();
		for (Coin coin : COINS_DESCENDING) {
			while (cents >= coin.cents && returnedCoins.get(coin) < values.get(coin)) {				
				cents -= coin.cents;
				returnedCoins = returnedCoins.add(coin);
			}
		}
		if (cents > 0) {
			return empty();
		}
		
		return of(returnedCoins); 
	}
	
	
	public int hashCode() {
		return values.hashCode();
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coins other = (Coins) obj;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}
	
	public String toString() {
		return values.toString();
	}
	
}
