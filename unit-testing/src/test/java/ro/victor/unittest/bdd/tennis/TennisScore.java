package ro.victor.unittest.bdd.tennis;

public class TennisScore {
	private int player1Score;
	private int player2Score;

	private static final String[] SCORE_NAMES =
		{ "Love", "Fifteen", "Thirty", "Forty" };

	public String getScore() {
		if (player1Score >= 3 && player2Score >= 3) {
			if (player1Score == player2Score)
				return "Deuce";
			else if ( player1Score - 1 == player2Score )
				return "Advantage Player1";
			else if ( player2Score - 1 == player1Score )
				return "Advantage Player2";
		}
		if (player1Score>=4 && player1Score-player2Score>=2)
			return "Game won by Player1";
		
		if (player2Score>=4 && player2Score-player1Score>=2)
			return "Game won by Player2";

		return SCORE_NAMES[player1Score] + "-" + SCORE_NAMES[player2Score];

	}

	public void point(String player) {
		if ("Player1".equals(player)) {
			player1Score++;
		} else if ("Player2".equals(player)) {
			player2Score++;
		} else
			throw new IllegalArgumentException();
	}

}
