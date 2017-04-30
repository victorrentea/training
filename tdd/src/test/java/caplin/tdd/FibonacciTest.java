package caplin.tdd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.*;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.Assert.*;

@RunWith(Parameterized.class)
public class FibonacciTest {

		private int expected;
		private int index;

		public FibonacciTest(int expected, int index) {
				this.expected = expected;
				this.index = index;
		}


		@Parameters
		public static Collection<Object[]> data() {
				return Arrays.asList(new Object[][]{
								{0, 0},
								{1, 1}
				});
		}

		@Test
		public void testFibonacci0() {
				int[] fibonacciSeries = new Fibonacci().getFibonacciSeries(1);

				assertEquals(0, fibonacciSeries[0]);
		}

		@Test
		public void testFibonacci1() {
				int[] fibonacciSeries = new Fibonacci().getFibonacciSeries(2);

				assertEquals(1, fibonacciSeries[1]);
		}

		@Test
		public void testFibonacci2() {
				int[] fibonacciSeries = new Fibonacci().getFibonacciSeries(3);

				assertEquals(1, fibonacciSeries[2]);
		}

		@Test
		public void testFibonacci8() {
				int[] fibonacciSeries = new Fibonacci().getFibonacciSeries(8);

				assertEquals(13, fibonacciSeries[7]);
		}

		@Test
		public void testFibonacci29() {
				int[] fibonacciSeries = new Fibonacci().getFibonacciSeries(29);

				assertEquals(317811, fibonacciSeries[28]);
		}

}
