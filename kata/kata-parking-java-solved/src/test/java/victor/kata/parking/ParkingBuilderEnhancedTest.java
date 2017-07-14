package victor.kata.parking;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import victor.kata.parking.ParkingBuilder;

public class ParkingBuilderEnhancedTest {

	@Test
	public void testRandomMethodOrder() {
		assertEquals(3 * 3 - 1, new ParkingBuilder()
				.withPedestrianExit(2)
				.withDisabledBay(3)
				.withSquareSize(3)
				.build().getAvailableBays());
	}
}
