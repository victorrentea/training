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
		return "  123"
				+ "\nA|" + String.valueOf(b[0])
				+ "\nB|" + String.valueOf(b[1])
				+ "\nC|" + String.valueOf(b[2]);
	}

	public void move(String place) {
		if (place.equals("A1")) {
			b[0][0]='X';
		} else {
			b[0][1] = 'X';
		}
	}
	



}
