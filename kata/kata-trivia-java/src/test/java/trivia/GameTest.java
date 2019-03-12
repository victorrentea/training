
package trivia;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import org.junit.Test;


public class GameTest {


	@Test
	public void caracterizationTest() {
		for (int seed = 1; seed < 10_000; seed++) {
			String expectedOutput = extractOutput(new Random(seed), new Game());
			String actualOutput = extractOutput(new Random(seed), new GameBetter());
			assertEquals(expectedOutput, actualOutput);
		}
	}

	private String extractOutput(Random rand, IGame aGame) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (PrintStream inmemory = new PrintStream(baos)) {
			System.setOut(inmemory);
			
			
			aGame.add("Chet");
			aGame.add("Pat");
			aGame.add("Sue");
			
			boolean notAWinner = false;
			do {
				aGame.roll(rand.nextInt(5) + 1);
				
				if (rand.nextInt(9) == 7) {
					notAWinner = aGame.wrongAnswer();
				} else {
					notAWinner = aGame.wasCorrectlyAnswered();
				}
				
			} while (notAWinner);
		}
		String output = new String(baos.toByteArray());
		return output;
	}
}
