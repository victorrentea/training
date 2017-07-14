package victor.kata.parking.slots;

public class DisabledSlot implements ParkingSlot {

	private final int index;
	private boolean free = true;
	
	public DisabledSlot(int index) {
		this.index = index;
	}

	public boolean isFree() {
		return free;
	}
	
	@Override
	public char getCharCode() {
		return free ? '@' : 'D';
	}

	
	@Override
	public boolean isDisabled() {
		return true;
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public void park(char carType) {
		free = false;
	}

	@Override
	public boolean unpark() {
		if (free) {
			return false;
		} else {
			free = true;
			return true;
		}
	}
}
