package victor.kata.parking.slots;

import java.nio.channels.IllegalSelectorException;

public class NormalSlot implements ParkingSlot {

	private final int index;
	private Character carType;
	
	public NormalSlot(int index) {
		this.index = index;
	}

	@Override
	public boolean isFree() {
		return carType == null;
	}
	
	@Override
	public char getCharCode() {
		return carType == null ? 'U' : carType;
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public void park(char carType) {
		if (!isFree()) {
			throw new IllegalSelectorException();
		}
		this.carType = carType;
	}

	@Override
	public boolean unpark() {
		if (carType == null) {
			return false;
		} else {
			carType = null;
			return true;
		}
	}

}
