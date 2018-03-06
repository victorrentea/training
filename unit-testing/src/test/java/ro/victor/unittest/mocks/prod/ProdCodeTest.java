package ro.victor.unittest.mocks.prod;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ProdCodeTest {
	
	public void metoda(Order order) {
//		order.setReference("x");
	}
	
	@Test
	public void testMetoda() {
		Order order = new Order();
		metoda(order);
		assertEquals("x", order.getReference());
	}

}
