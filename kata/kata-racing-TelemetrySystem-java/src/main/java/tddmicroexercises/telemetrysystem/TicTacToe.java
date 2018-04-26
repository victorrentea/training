package tddmicroexercises.telemetrysystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
	
	private final static List<Function<Integer, Point>> FORMULAS = makeFormulas();
	
	
	public Integer getWinner() {
		
		for (Function<Integer, Point> formula : FORMULAS) {
			Set<Character> s = signs(formula);
			if (s.size() == 1 && s.iterator().next()!='_') {
				return s.iterator().next()== 'X'?0:1;
			}
		}
		return null;
	}
	private static List<Function<Integer, Point>> makeFormulas() {
		List<Function<Integer, Point>> formulas = new ArrayList<>();
		formulas.add(i->new Point(i,i));
		formulas.add(i->new Point(i,2-i));
		for (int j = 0; j < 3; j++) {
			final int jj = j;
			formulas.add(i->new Point(jj,i));
			formulas.add(i->new Point(i,jj));
		}
		return formulas;
	}
	private Set<Character> signs(Function<Integer, Point> coord) {
		Set<Character> s = new HashSet<>();
		for (int i=0;i<3;i++) {
			s.add(b[coord.apply(i).getRow()][coord.apply(i).getCol()]);
		}
		return s;
	}
	
	private static class Point {
		private final int row,col;

		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}

		public int getRow() {
			return row;
		}

		public int getCol() {
			return col;
		}
		
		
	}
	



}
