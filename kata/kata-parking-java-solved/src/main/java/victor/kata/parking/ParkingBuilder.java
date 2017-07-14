package victor.kata.parking;

import java.util.HashSet;
import java.util.Set;

/**
 * Builder class to get a parking instance
 */
public class ParkingBuilder {

	private int squareSize;
	private Set<Integer> pedestrianEntries = new HashSet<>();
	private Set<Integer> disabledBays = new HashSet<>();

	public ParkingBuilder withSquareSize(int size) {
		squareSize = size;
		return this;
    }

    public ParkingBuilder withPedestrianExit(int pedestrianExitIndex) {
    	pedestrianEntries.add(pedestrianExitIndex);
    	return this;
    }

    public ParkingBuilder withDisabledBay(int disabledBayIndex) {
    	disabledBays.add(disabledBayIndex);
    	return this;
    }

    public Parking build() {
        return new Parking(squareSize, pedestrianEntries, disabledBays);
    }
}
