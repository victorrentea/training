package trivia;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ParameterizedJUnitTestsDemo {
	
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(
				new Object[]{0}, 
				new Object[]{1}, 
				new Object[]{2}, 
				new Object[]{3});
	}
	
	private final int param;

	public ParameterizedJUnitTestsDemo(int param) {
		this.param = param;
	}
	
	@Test
	public void isEven() {
		assertEquals(param + " should be even", 0, param % 2);
	}

}
