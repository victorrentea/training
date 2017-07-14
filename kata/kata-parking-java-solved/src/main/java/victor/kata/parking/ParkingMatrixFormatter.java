package victor.kata.parking;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.List;

import victor.kata.parking.slots.ParkingSlot;

public class ParkingMatrixFormatter {
	private final int edge;
	private final List<ParkingSlot> slots;
	public ParkingMatrixFormatter(int edge, List<ParkingSlot> slots) {
		this.edge = edge;
		this.slots = slots;
	}
	
	public String toMatrixString() {
    	// TODO research if splitterator or vavr library can help here
    	List<String> rows = new ArrayList<>();
    	for (int rowIndex = 0; rowIndex < edge; rowIndex ++) {
    		String line = "";
    		for (int colIndex = 0; colIndex < edge; colIndex ++) {
    			int globalIndex = rowIndex*edge;
    			if (rowIndex % 2 == 0) {
    				globalIndex += colIndex;
    			} else {
    				globalIndex += edge - 1 - colIndex;
    			}
    			line += slots.get(globalIndex).getCharCode();
    		}
    		rows.add(line);
    	}
    	return rows.stream().collect(joining("\n"));
	}
}
