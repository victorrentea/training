package ro.victor.tennis;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TennisGameShould {

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
	
	@Test
	public void displayLoveLoveForInitialGame() {
		assertEquals("Love-Love", getScore(0, 0));
	}
	
	@Test
	public void displayFifteenLoveWhenPlayer1Scores1Point() {
		assertEquals("Fifteen-Love", getScore(1, 0));
	}
	
	@Test
	public void displayLoveFifteenWhenPlayer2Scores1Point() {
		assertEquals("Love-Fifteen", getScore(0, 1));
	}
	
	@Test
	public void displayThirtyLoveWhenPlayer1Scores2Point() {
		assertEquals("Thirty-Love", getScore(2, 0));
	}
	@Test
	public void displayDeuceWhenPlayer1Scores3PointAndPlayer2Scores3Point() {
		assertEquals("Deuce", getScore(3, 3));
	}
	
	

	@Test
	public void displayFortyLoveWhenPlayer1Scores3Point() {
		assertEquals("Forty-Love", getScore(3, 0));
	}
}
