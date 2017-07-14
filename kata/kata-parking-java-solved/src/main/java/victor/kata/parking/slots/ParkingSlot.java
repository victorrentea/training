package victor.kata.parking.slots;

public interface ParkingSlot {

	int getIndex();
	void park(char carType); // C-Q Separation Principle in doubt
	boolean unpark();
	boolean isFree();
	boolean isDisabled();
	char getCharCode();
}
