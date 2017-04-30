package caplin.tdd;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by nicolaes on 07/01/2016.
 */
public class FizzBuzzTest {

		@Test
		public void oneFizzBuzzShouldBeOne() {
				Assert.assertEquals("1", getFizzBuzzStringAtPosition(0));
//				Assert.assertN
		}

		@Test
		public void twoFizzBuzzShouldBeTwo() {
				Assert.assertEquals("2", getFizzBuzzStringAtPosition(1));
		}

		private String getFizzBuzzStringAtPosition(int position) {
				return new FizzBuzz().generateFizzBuzz().split(",")[position];
		}

		@Test
		public void threeFizzBuzzShouldBeFizz() {
				Assert.assertEquals("Fizz", getFizzBuzzStringAtPosition(2));
		}

}
