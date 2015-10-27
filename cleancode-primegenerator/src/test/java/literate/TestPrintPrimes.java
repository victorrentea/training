package literate;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPrintPrimes {

	private PrintStream out;

	@Before
	public void setup() throws Exception {
		out = System.out;
		System.setOut(new PrintStream(new FileOutputStream("actual.txt")));
	}

	@After
	public void teardown() {
		System.setOut(out);
		new File("actual.txt").delete();
	}

	@Test
	public void testMain() throws Exception {
		literate.named.PrintPrimes.main(null);
		
		try (BufferedReader actual = new BufferedReader(new FileReader("actual.txt")); // Try with resources (java 7)
			BufferedReader expected = new BufferedReader(new FileReader("expected.txt"))) {
			String expectedLine;
			int lineNo = 1;
			while ((expectedLine = expected.readLine()) != null) {
				assertEquals("Matches line #" + lineNo ++, expectedLine, actual.readLine());
			}
			assertEquals(null, actual.readLine());
		}
	}

}
