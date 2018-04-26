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
	
}
