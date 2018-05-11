package ro.victor.unittest.parameterized;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class PureFunctionTest {
	
	static class Order {
		
		private String firstName, lastName;
		
		public Order() {
			System.out.println("Un order nou");
		}
		

		public Order(String firstName, String lastName) {
			this.firstName = firstName;
			this.lastName = lastName;
		}


		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String toString() {
			return "Order [firstName=" + firstName + ", lastName=" + lastName + "]";
		}

		
	}
	
	Order order = new Order();
	
	
	@Test
	public void comparandObiecteToString() {
		Order order1 = new Order("f1", "l1");
		Order order2 = new Order("f2", "l1");
		
		assertEquals(order1.toString(), order2.toString());
	}
	
	@Before
	public void x() {
		System.out.println("Rulez inaintea fiecarui test");
	}
	
	public PureFunctionTest() {
		System.out.println("Uaaaa, Uaaa!!");
	}
	
	// cod de prod
	int f(int x) {
		return x * x;
	}
	
	@Test
	public void f0returns0() {
		assertEquals(0,f(0));
	}
	@Test
	public void f1returns1() {
		assertEquals(1,f(1));
	}
	@Test
	public void f2returns4() {
		assertEquals(4,f(2));
	}
	@Test
	public void fMinus2returns4() {
		assertEquals(4,f(-2));
	}
	@Test
	public void desteptaciune() {
		for (int i = -2 ;i<=2;i++) {
			assertEquals(i * i, f(i));
		}
	}
	@Test
	public void cuInputuriSiExpectedExplicite() {
		int []input = {-2,0,1,2};
		int []expected = {4,0,1,4};
		for (int i = 0 ;i<input.length;i++) {
			assertEquals(expected[i], f(input[i]));
		}
	}
}
