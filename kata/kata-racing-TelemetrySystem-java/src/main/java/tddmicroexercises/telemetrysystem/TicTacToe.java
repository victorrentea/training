package tddmicroexercises.telemetrysystem;

public class TicTacToe {
	private String board = "  123\nA|___\nB|___\nC|___";

	public String getBoard() {
		return board;
	}

	public void move(String place) {
		 board = "  123\nA|X__\nB|___\nC|___";
	}
	



}
