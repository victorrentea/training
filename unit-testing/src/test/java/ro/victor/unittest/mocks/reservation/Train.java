package ro.victor.unittest.mocks.reservation;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Train {
	
	private final Map<String, SeatData> seats;

	public Train(Map<String, SeatData> seats) {
		this.seats = seats;
	}	

	public int getTotalBookedSeats() {
		return (int) seats.values().stream().filter(SeatData::isBooked).count();
	}

	public List<String> getAvailableSeatsInSameCoach(int count) {
		Map<String, SeatData> availableSeats = seats.entrySet().stream()
//				.filter(entry -> !entry.getValue().isBooked())
				.limit(2)
				.collect(toMap(Entry::getKey, Entry::getValue));
		if (availableSeats.isEmpty()) {
			return emptyList();
		} else {
			return new ArrayList<>(availableSeats.keySet());
		}
	}

	public int getTotalSeats() {
		return seats.size();
	}
}
