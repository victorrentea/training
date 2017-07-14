package victor.kata.parking.slots;

public class PedestrianSlot implements ParkingSlot {

	private final int index;
	
	public PedestrianSlot(int index) {
		this.index = index;
	}

	@Override
	public boolean isFree() {
		return false;
	}
	
	@Override
	public boolean isDisabled() {
		return false; // Liskov substitudion principle challenge. "Bloated Interface" syndrome
	}
	
	@Override
	public char getCharCode() {
		return '=';
	}

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void park(char carType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean unpark() {
		// TODO Auto-generated method stub
		return false;
	}

}
