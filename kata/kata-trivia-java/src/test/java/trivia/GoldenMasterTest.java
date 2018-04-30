package trivia;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class GoldenMasterTest {
	
	@Parameters
	public static Collection<Object[]> data() {
		return IntStream.range(0, 10).mapToObj(i -> new Object[]{i}).collect(toList());
	}
	
	final int seed;
	
	public GoldenMasterTest(int seed) {
		this.seed = seed;
	}

	@Test
	public void m() {
		String expectedOutput = CapturingSystemOut.captureOutput(() -> runOld(seed));
		String actualOutput = CapturingSystemOut.captureOutput(() -> runNew(seed));
		assertEquals(expectedOutput, actualOutput);
	}
	
	private void runOld(int seed) {
		Game game = new Game();
		game.add("Chet");
		game.add("Pat");
		game.add("Sue");
		
		Random rand = new Random(seed);
		
		boolean notAWinner;
		do {
			game.roll(rand.nextInt(5) + 1);
			
			if (rand.nextInt(9) == 7) {
				notAWinner = game.wrongAnswer();
			} else {
				notAWinner = game.wasCorrectlyAnswered();
			}
			
		} while (notAWinner);
	}
	private void runNew(int seed) {
		BetterGame game = new BetterGame();
		game.add("Chet");
		game.add("Pat");
		game.add("Sue");
		
		Random rand = new Random(seed);
	
		boolean notAWinner;
		do {
			game.roll(rand.nextInt(5) + 1);
			
			if (rand.nextInt(9) == 7) {
				notAWinner = !game.wrongAnswer();
			} else {
				notAWinner = !game.wasCorrectlyAnswered();
			}
			
		} while (notAWinner);
	}

}
