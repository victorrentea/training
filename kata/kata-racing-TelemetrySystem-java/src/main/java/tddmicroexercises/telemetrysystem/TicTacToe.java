package tddmicroexercises.telemetrysystem;

import java.util.Arrays;

public class TicTacToe {
//	private String board = ;
	char [][] b = new char [3][3];
	int player = 0;

	public TicTacToe() {
		for (char[] row : b) {
			Arrays.fill(row, '_');	
		}
	}
	public String getBoard() {
		return "  123"
				+ "\nA|" + String.valueOf(b[0])
				+ "\nB|" + String.valueOf(b[1])
				+ "\nC|" + String.valueOf(b[2]);
	}

	public void move(String place) {
		int row = place.charAt(0) - 'A';
		int col = place.charAt(1) - '1';
		if (b[row][col] != '_') {
			throw new IllegalArgumentException("Taken");
		}
		b[row][col]=player ==0?'X':'0';
		player = 1-player;
	}
	public Integer getWinner() {
		if (b[0][0] == b[1][1] && b[1][1] == b[2][2] && b[0][0] != '_') {
			return b[0][0]== 'X'?0:1;
		}
		return null;
	}
	



}
