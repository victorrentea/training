package victor.kata.parking;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import victor.kata.parking.slots.DisabledSlot;
import victor.kata.parking.slots.NormalSlot;
import victor.kata.parking.slots.ParkingSlot;
import victor.kata.parking.slots.PedestrianSlot;


/**
 * Handles the parking mechanisms: park/unpark a car (also for disabled-only bays) and provides a string representation of its state.
 */
public class Parking {
	
	private List<ParkingSlot> slots;
	
	private Set<Integer> pedestrianEntries;

	private int edge;
	
	public Parking(int edge, Set<Integer> pedestrianEntries, Set<Integer> disabledBays) {
		if (edge < 0) throw new IllegalArgumentException("Negative square size");
		validatePedestrianEntries(edge, pedestrianEntries);
		validateDisabledBays(edge, disabledBays);
		validateOverlappingIndexes(pedestrianEntries, disabledBays);

		this.edge = edge;
		this.pedestrianEntries = pedestrianEntries;
		this.slots = buildInitialSlots(edge, pedestrianEntries, disabledBays);
	}

	private List<ParkingSlot> buildInitialSlots(int edge, Set<Integer> pedestrianEntries, Set<Integer> disabledBays) {
		List<ParkingSlot> initialSlots = new ArrayList<>();
		for (int gi = 0; gi < edge * edge; gi++) {
			if (pedestrianEntries.contains(gi)) {
				initialSlots.add(new PedestrianSlot(gi));
			} else if (disabledBays.contains(gi)) {
				initialSlots.add(new DisabledSlot(gi));
			} else {
				initialSlots.add(new NormalSlot(gi));
			}
		}
		return initialSlots;
	}
	
	private void validateOverlappingIndexes(Set<Integer> pedestrianEntries, Set<Integer> disabledBays) {
		Set<Integer> intersection = pedestrianEntries.stream()
				.filter(i -> disabledBays.contains(i))
				.collect(toSet());
		if (!intersection.isEmpty()) {
			throw new IllegalArgumentException("Identic values present in both disabled bays and pedestrian exit sets: " + intersection);
		}
	}

	private void validateDisabledBays(int length, Set<Integer> disabledBays) {
		Set<Integer> outOfBoundDisabled = disabledBays.stream()
				.filter(i -> i >= length * length  || i < 0)
				.collect(toSet());
		if (!outOfBoundDisabled.isEmpty()) {
			throw new IllegalArgumentException("Some disabled bays indexes were out of bound (greater than " + length * length + "): " + outOfBoundDisabled);
		}
	}

	private void validatePedestrianEntries(int length, Set<Integer> pedestrianEntries) {
		Set<Integer> outOfBoundPedestrian = pedestrianEntries.stream()
				.filter(i -> i >= length * length || i < 0)
				.collect(toSet());
		if (!outOfBoundPedestrian.isEmpty()) {
			throw new IllegalArgumentException("Some pedestrian indexes were out of bound (greater than " + length * length + "): " + outOfBoundPedestrian);
		}
	}
	
	
	private int getDistanceToClosestPedestrian(ParkingSlot parkingSlot) {
		return pedestrianEntries.stream()
				.mapToInt(pedIndex -> distance(parkingSlot.getIndex(), pedIndex))
				.min()
				.getAsInt();
	}
	private int distance(int gi1, int gi2) {
		return Math.abs(gi1 - gi2); // easy to extend to support 2-dimensional proximity.
	}
	
    /**
     * Park the car of the given type ('D' being dedicated to disabled people) in closest -to pedestrian exit- and first (starting from the parking's entrance)
     * available bay. Disabled people can only park on dedicated bays.
     *
     *
     * @param carType
     *            the car char representation that has to be parked
     * @return bay index of the parked car, -1 if no applicable bay found
     */
    public int parkCar(char carType) { // Command Query Separation violation of the API makes the method ugly to implement
    	Stream<ParkingSlot> allFreeSlots = allFreeSlotsForCar(carType == 'D');
    	Optional<ParkingSlot> bestSlotOpt = allFreeSlots.min(comparing(this::getDistanceToClosestPedestrian));
    	if (!bestSlotOpt.isPresent()) {
    		return -1;
    	}
    	ParkingSlot bestSlot = bestSlotOpt.get();
    	bestSlot.park(carType);
    	return bestSlot.getIndex();
    }


	private Stream<ParkingSlot> allFreeSlotsForCar(boolean searchForDisabled) {
    	Stream<ParkingSlot> allFreeSlots = slots.stream().filter(ParkingSlot::isFree);
    	if (searchForDisabled) {
    		allFreeSlots = allFreeSlots.filter(ParkingSlot::isDisabled);
    	}
		return allFreeSlots;
	}

    /**
     * Unpark the car from the given index
     *
     * @param index
     * @return true if a car was parked in the bay, false otherwise
     */
    public boolean unparkCar(int index) {
        return slots.get(index).unpark();
    }

    /**
     * Print a 2-dimensional representation of the parking with the following rules:
     * <ul>
     * <li>'=' is a pedestrian exit
     * <li>'@' is a disabled-only empty bay
     * <li>'U' is a non-disabled empty bay
     * <li>'D' is a disabled-only occupied bay
     * <li>the char representation of a parked vehicle for non-empty bays.
     * </ul>
     * U, D, @ and = can be considered as reserved chars.
     *
     * Once an end of lane is reached, then the next lane is reversed (to represent the fact that cars need to turn around)
     *
     * @return the string representation of the parking as a 2-dimensional square. Note that cars do a U turn to continue to the next lane.
     */
    @Override
    public String toString() { 
    	return new ParkingMatrixFormatter(edge, slots).toMatrixString();
    }
    
    /**
     * @return the number of available parking bays left
     */
    public int getAvailableBays() {
        return (int) slots.stream()
        		.filter(ParkingSlot::isFree)
        		.count();
    }

}
