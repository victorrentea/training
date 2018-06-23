package junkomat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MoneyMachineTest {

	private MoneyMachine machine = new MoneyMachine();
	
	
	@Test
	public void acceptsPaymentAndTotalCentsGrows() {
		Coins rest = machine.acceptPayment(10, new Coins(Coin.TEN_CENTS));
		assertEquals(0, rest.sumTotalCents());
		assertEquals(10, machine.sumTotalCents());
	}
	
	@Test
	public void givenEmptyStash_whenPayed2x1EUR_forPrice100_returns_1EUR() {
		Coins rest = machine.acceptPayment(100, new Coins(Coin.ONE_EURO, Coin.ONE_EURO));
		assertEquals(new Coins(Coin.ONE_EURO), rest);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void cannotReturnChange() {
		machine.acceptPayment(80, new Coins(Coin.ONE_EURO));
	}
	@Test
	public void canReturnChange() {
		machine.refill(new Coins(Coin.TWENTY_CENTS));
		machine.acceptPayment(80, new Coins(Coin.ONE_EURO));
	}
	
}
