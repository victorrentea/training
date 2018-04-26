package tddmicroexercises.telemetrysystem;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TicTacToeTest {
	@Test
	public void emptyBoard() {
		assertEquals("  123\nA|___\nB|___\nC|___", TicTacToe.getBoard());
	}
	
}
