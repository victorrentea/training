package junkomat;

import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class JunkomatTest {
	private Junkomat junkomat = new Junkomat();
	
	@Test
	public void refill() {
		Map<Code, ProductInfo> products = new HashMap<>();
		products.put(new Code(1), new ProductInfo("Pepsi 0.5", 20, 2));
		products.put(new Code(2), new ProductInfo("Cola 0.5", 20, 1));
		junkomat.refillProducts(products);
		assertEquals(3, junkomat.sumTotalProducts());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidSelection() {
		junkomat.buyProduct(new Code(1), new Coins(Coin.TWENTY_CENTS));
	}
	
	@Test
	public void cannotProvideRest_throws_andLeavesTotalCentsUnchanged() {
		junkomat.refillProducts(singletonMap(new Code(1), new ProductInfo("Pepsi 0.5", 20, 1)));
		try {
			junkomat.buyProduct(new Code(1), new Coins(Coin.FIFTY_CENTS));
			fail("Should've thrown");
		} catch (IllegalArgumentException e) {
			// ok
		}
		assertEquals(0, junkomat.sumTotalCents());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void outOfStock() {
		junkomat.refillProducts(singletonMap(new Code(1), new ProductInfo("Pepsi 0.5", 20, 1)));
		junkomat.buyProduct(new Code(1), new Coins(Coin.TWENTY_CENTS));
		junkomat.buyProduct(new Code(1), new Coins(Coin.TWENTY_CENTS));
	}
	
	@Test
	public void simpleBuy() {
		junkomat.refillProducts(singletonMap(new Code(1), new ProductInfo("Pepsi 0.5", 20, 1)));
		ProductAndRest rez = junkomat.buyProduct(new Code(1), new Coins(Coin.TWENTY_CENTS));
		assertEquals(new Coins(), rez.getReturnedCoins());
		assertEquals("Pepsi 0.5", rez.getProductName());
		assertEquals(20, junkomat.sumTotalCents());
		assertEquals(0, junkomat.sumTotalProducts());
	}
	@Test
	public void complex() { // 50 + 20 + 10
		junkomat.refillProducts(singletonMap(new Code(1), new ProductInfo("Pepsi 0.5", 20, 2)));
		junkomat.refillMoney(new Coins(Coin.TWENTY_CENTS, Coin.TWENTY_CENTS, Coin.TEN_CENTS, Coin.TEN_CENTS));
		assertEquals(60, junkomat.sumTotalCents());
		
		ProductAndRest rez1 = junkomat.buyProduct(new Code(1), new Coins(Coin.FIFTY_CENTS));
		assertEquals(new Coins(Coin.TWENTY_CENTS, Coin.TEN_CENTS), rez1.getReturnedCoins());
		assertEquals("Pepsi 0.5", rez1.getProductName());
		
		ProductAndRest rez2 = junkomat.buyProduct(new Code(1), new Coins(Coin.ONE_EURO));
		assertEquals(new Coins(Coin.FIFTY_CENTS, Coin.TWENTY_CENTS, Coin.TEN_CENTS), rez2.getReturnedCoins());
		assertEquals("Pepsi 0.5", rez2.getProductName());
		
		
		assertEquals(60 + 40, junkomat.sumTotalCents());
		assertEquals(0, junkomat.sumTotalProducts());
	}
}
