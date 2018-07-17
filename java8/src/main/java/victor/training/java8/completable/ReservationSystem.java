package victor.training.java8.completable;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.Random;

public class ReservationSystem {

	List<String> airlines = asList("Tarom", "AirFrance", "WizzAir");
	List<String> hotels = asList("Accord", "IBIS", "Hilton");

	private void run() {

	}

	private AirlineOffer queryAirline(String airline) {
		return new AirlineOffer(airline, 10 + new Random().nextInt(10));
	}

	private HotelOffer queryHotel(String hotel) {
		return new HotelOffer(hotel, 30 + new Random().nextInt(20));

	}

	public static void main(String[] args) {
		new ReservationSystem().run();
	}
}

class AirlineOffer {
	private final String name;
	private final int price;

	public AirlineOffer(String name, int price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}
}

class HotelOffer {
	private final String name;
	private final int price;

	public HotelOffer(String name, int price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}
}