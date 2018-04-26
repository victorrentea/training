package tddmicroexercises.telemetrysystem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

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
		Set<Character> s = signs(i->i, i->i);
		if (s.size() == 1 && s.iterator().next()!='_') {
			return s.iterator().next()== 'X'?0:1;
		}
		s = signs(i->0, i->i);
		if (s.size() == 1 && s.iterator().next()!='_') {
			return s.iterator().next()== 'X'?0:1;
		}
		return null;
	}
	private Set<Character> signs(
			Function<Integer, Integer> row,
			Function<Integer, Integer> col
			) {
		Set<Character> s = new HashSet<>();
		for (int i=0;i<3;i++) {
			s.add(b[row.apply(i)][col.apply(i)]);
		}
		return s;
	}
	



}
