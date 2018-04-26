package tddmicroexercises.telemetrysystem;

import java.util.Arrays;

public class TicTacToe {
//	private String board = ;
	char [][] b = new char [3][3];

	public TicTacToe() {
		for (char[] row : b) {
			Arrays.fill(row, '_');	
		}
	}
	public String getBoard() {
		String board = "  123\nA|" + String.valueOf(b[0])
		+ "\nB|"+String.valueOf(b[1])+"\nC|" + String.valueOf(b[2]);
		return board;
	}

	public void move(String place) {
		 b[0][0]='X';
	}
	



}
