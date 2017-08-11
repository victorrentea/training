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
		return IntStream.range(0, 1000).mapToObj(i -> new Integer[]{i}).collect(toList());
	}
	
	private final int seed;

	public GoldenMasterTest(int seed) {
		this.seed = seed;
	}
	
	public static class GameToRefactor extends Game {
		// TODO do not extend, but copy the implem here and start refactoring it
	}

	@Test
	public void testOutputIsTheSameAsOriginal() {
		String expectedOutput = CapturingSystemOutDemo.captureOutput(() -> playGame(new Game(), new Random(seed)));
		String actualOutput = CapturingSystemOutDemo.captureOutput(() -> playGame(new GameToRefactor(), new Random(seed)));
		assertEquals(expectedOutput, actualOutput);
	}

	private void playGame(Game game, Random random) {
		game.add("Chet");
		game.add("Pat");
		game.add("Sue");
		
		boolean notAWinner;
		do {
			game.roll(random.nextInt(5) + 1);
			if (random.nextInt(9) == 7) {
				notAWinner = game.wrongAnswer();
			} else {
				notAWinner = game.wasCorrectlyAnswered();
			}
			
		} while (notAWinner);
	}
}
