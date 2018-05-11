package ro.victor.unittest.parameterized;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class TestParametrizabile {
	
	private int input;
	private int expected;

	// cod de prod
	int f(int x) {
		return x * x;
	}
	
	public TestParametrizabile(int input, int expected) {
		this.input = input;
		this.expected = expected;
	}
	
	@Parameters(name="f({0})={1}")
	public static List<Object[]> parametriiExecutiei() {
		return asList(
				new Object[]{-2, 4},
				new Object[]{0, 0},
				new Object[]{1, 1},
				new Object[]{2, 9}
				);
	}
	
	@Test
	public void verifica() {
		assertEquals(expected, f(input));
	}
	
}
