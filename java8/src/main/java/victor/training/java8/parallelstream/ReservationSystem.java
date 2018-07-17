package victor.training.java8.parallelstream;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.Random;

public class ReservationSystem {

	List<String> airLines = asList("Tarom", "AirFrance", "WizzAir");
	
	private void run() {
		
	}
	
	public static void main(String[] args) {
		
	}
}


class AirLineOffer {
	private final String name;
	public AirLineOffer(String name) {
		this.name = name;
	}
	public int getPrice() {
		return 20 + new Random().nextInt(20);
	}
}

class Hotel {
	private final String name;

	public Hotel(String name) {
		this.name = name;
	}
	public int getPrice() {
		return 40 + new Random().nextInt(60);
	}
}