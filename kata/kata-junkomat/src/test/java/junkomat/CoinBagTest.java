package junkomat;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
import static java.util.Collections.unmodifiableMap;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

public class CoinBagTest {

	private static final Map<Coin, Integer> UNLIMITED_COINS = 
			unmodifiableMap(Stream.of(Coin.values()).collect(toMap(identity(), x -> Integer.MAX_VALUE)));
	private Coins unlimitedCoinsBag = new Coins(UNLIMITED_COINS);
	
	
	@Test
	public void returns50() {
		assertEquals(new Coins(Coin.FIFTY_CENTS), unlimitedCoinsBag.computeChange(50).get());
	}
	@Test
	public void returns60() {
		assertEquals(new Coins(Coin.FIFTY_CENTS, Coin.TEN_CENTS), unlimitedCoinsBag.computeChange(60).get());
	}
	@Test
	public void returns80() {
		assertEquals(new Coins(Coin.FIFTY_CENTS, Coin.TWENTY_CENTS, Coin.TEN_CENTS), unlimitedCoinsBag.computeChange(80).get());
	}
	@Test
	public void returns20() {
		assertEquals(new Coins(Coin.TWENTY_CENTS), unlimitedCoinsBag.computeChange(20).get());
	}
	@Test
	public void returns40() {
		assertEquals(new Coins(Coin.TWENTY_CENTS, Coin.TWENTY_CENTS), unlimitedCoinsBag.computeChange(40).get());
	}
	@Test
	public void returns100() {
		assertEquals(new Coins(Coin.ONE_EURO), unlimitedCoinsBag.computeChange(100).get());
	}
	@Test
	public void returns200() {
		assertEquals(new Coins(Coin.TWO_EUROS), unlimitedCoinsBag.computeChange(200).get());
	}
	
	@Test
	public void totalCents() {
		assertEquals(380, new Coins(Coin.TWO_EUROS, Coin.ONE_EURO, Coin.FIFTY_CENTS, Coin.TWENTY_CENTS,Coin.TEN_CENTS).sumTotalCents());
	}
	
	@Test
	public void whenShouldReturn10Cents_butUnavaiable_throws() {
		assertFalse(new Coins().computeChange(10).isPresent());
	}
	
}
