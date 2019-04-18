package ro.victor.tennis;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class TennisGameParametered {

	private static String getScore(int player1Score, int player2Score) {
		TennisGame tennisGame = new TennisGame(); 
		setPlayerScore(1, player1Score, tennisGame);
		setPlayerScore(2, player2Score, tennisGame);
		return tennisGame.score();
	}

	private static void setPlayerScore(int player, int score, TennisGame tennisGame) {
		for (int i = 0; i < score; i++) {
			tennisGame.point(player);
		}
	}
	
	private final String expectedScoreString;
	private final int player1Score;
	private final int player2Score;
	
	public TennisGameParametered(String expectedScoreString, 
			int player1Score, int player2Score) {
		this.expectedScoreString = expectedScoreString;
		this.player1Score = player1Score;
		this.player2Score = player2Score;
	}

	@Parameters(name= "Expects score={0} when player1={1} and player2={2}")
	public static List<Object[]> parameters() {
		return asList(
				new Object[] {"Love-Love",0,0},
				new Object[] {"Fifteen-Love",1,0},
				new Object[] {"Thirty-Love",2,0},
				new Object[] {"Forty-Love",3,0},
				new Object[] {"Love-Fifteen",0,1},
				new Object[] {"Deuce",3,3},
				new Object[] {"Deuce",4,4},
				new Object[] {"Advantage Player1",4,3},
				new Object[] {"Advantage Player2",3,4},
				new Object[] {"Game Won Player1",4,2},
				new Object[] {"Game Won Player1",5,3},
				new Object[] {"Game Won Player1",4,0},
				new Object[] {"Game Won Player2",0,4}
				);
	}
	
	@Test
	public void test() {
		assertEquals(expectedScoreString, getScore(player1Score, player2Score));
	}
}
