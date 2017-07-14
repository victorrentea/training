package victor.kata.parking;

import static java.util.Collections.EMPTY_SET;
import static java.util.Collections.singleton;

import org.junit.Test;

import victor.kata.parking.Parking;

@SuppressWarnings("unchecked")
public class ParkingConstraintsTest {

	@Test
	public void ok() {
		new Parking(2, singleton(2), EMPTY_SET);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void negativeSquareSize_throws() {
		new Parking(-1, singleton(0), EMPTY_SET);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void outOfBoundsPedestrian_throws() {
		new Parking(2, singleton(1000), EMPTY_SET);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void outOfBoundsDisabled_throws() {
		new Parking(2,  singleton(0), singleton(1000));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void negativeDisabled_throws() {
		new Parking(2,  singleton(0), singleton(-2));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void negativePedestrian_throws() {
		new Parking(2,  singleton(-2), EMPTY_SET);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void duplicatedDisabledPedestrian_throws() {
		new Parking(2,  singleton(0), singleton(0));
	}
}
