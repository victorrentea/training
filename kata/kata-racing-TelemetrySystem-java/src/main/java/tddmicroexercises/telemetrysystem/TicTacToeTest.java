package tddmicroexercises.telemetrysystem;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TicTacToeTest {
	private TicTacToe tic = new TicTacToe();
	
	@Test
	public void emptyBoard() {
		assertEquals("  123\nA|___\nB|___\nC|___", tic.getBoard());
	}
	
	@Test
	public void boardAfterA1() {
		tic.move("A1");
		assertEquals("  123\nA|X__\nB|___\nC|___", tic.getBoard());
	}
	
	@Test
	public void boardAfterA2() {
		tic.move("A2");
		assertEquals("  123\nA|_X_\nB|___\nC|___", tic.getBoard());
	}
	@Test
	public void boardAfterA3() {
		tic.move("A3");
		assertEquals("  123\nA|__X\nB|___\nC|___", tic.getBoard());
	}
	@Test
	public void boardAfterB2() {
		tic.move("B2");
		assertEquals("  123\nA|___\nB|_X_\nC|___", tic.getBoard());
	}
	
	@Test
	public void boardAfterA1A2() {
		tic.move("A1");
		tic.move("A2");
		assertEquals("  123\nA|X0_\nB|___\nC|___", tic.getBoard());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void A1A1throws() {
		tic.move("A1");
		tic.move("A1");
	}
	
	@Test
	public void player1wins() {
		tic.move("A1");
		assertEquals(null, tic.getWinner());
		tic.move("A2");
		tic.move("B2");
		tic.move("B3");
		tic.move("C3");
		assertEquals((Integer)0, tic.getWinner());
	}
	@Test
	public void player1winsAgain() {
		tic.move("C1");
		assertEquals(null, tic.getWinner());
		tic.move("A2");
		tic.move("B2");
		tic.move("B3");
		tic.move("A3");
		assertEquals((Integer)0, tic.getWinner());
	}
	
	@Test
	public void player2wins() {
		tic.move("C1");
		assertEquals(null, tic.getWinner());
		tic.move("A1");
		tic.move("B2");
		tic.move("A2");
		tic.move("B3");
		tic.move("A3");
		assertEquals((Integer)1, tic.getWinner());
	}
	
	
}
