package ro.victor.tennis;

public class TennisGame {
	private int player1Score;
	private int player2Score;

	public String score() {
		if (player1Score == player2Score && player1Score >=3) {
			return "Deuce";
		}
		
		if (player1Score >= 3 && player2Score >= 3) {
			if (player1Score - player2Score == 1) {
				return "Advantage Player1";
			}
			if (player2Score - player1Score == 1) {
				return "Advantage Player2";
			}
		}
		if (player1Score >= 4 && player1Score - player2Score >= 2) {
			return "Game Won Player1";
		}
		if (player2Score >= 4 && player2Score - player1Score >= 2) {
			return "Game Won Player2";
		}
		return scoreToString(player1Score)+ 
				"-" + 
				scoreToString(player2Score);
	}

	private String scoreToString(int playerScore) {
		switch (playerScore) {
		case 0: return "Love";
		case 1: return  "Fifteen";
		case 2: return  "Thirty";
		case 3: return  "Forty";
		default: throw new IllegalArgumentException();
		}
	}

	public void point(int playerNo) {
		if (playerNo == 1) {
			player1Score++;
		} else {
			player2Score++;
		}
	}

}
