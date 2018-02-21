package ro.victor.unittest.mocks.reservation;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toMap;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TrainTest {
	
	@Test
	public void whenNoSeatsBooked_getTotalBookedSeats_returns0() {
		assertEquals(0, aTrain().getTotalBookedSeats());
	}
	
	@Test
	public void when1SeatBooked_getTotalBookedSeats_returns1() {
		assertEquals(1, aTrain("A1*").getTotalBookedSeats());
	}
	
	@Test
	public void whenNoSeats_getTotalSeats_returns0() {
		assertEquals(0, aTrain().getTotalSeats());
	}
	
	@Test
	public void given1Seat_getTotalSeats_returns1() {
		assertEquals(1, aTrain("A1").getTotalSeats());
	}
	@Test
	public void givenNoSeats_getAvailableSeatsInSameCoach_returnsNothing() {
		assertEquals(emptyList(), aTrain().getAvailableSeatsInSameCoach(1));
	}
	@Test
	public void givenA1Available_get1AvailableSeatInSameCoach_returnsA1() {
		Train train = aTrain("A1");
		List<String> actual = train.getAvailableSeatsInSameCoach(1);
		assertEquals(singletonList("A1"), actual);
	}
	
	@Test
	public void givenA1A2A3Available_getAvailableSeatsInSameCoach_returnsA1A2() {
		Train train = aTrain("A1","A2","A3");
		List<String> actual = train.getAvailableSeatsInSameCoach(2);
		assertEquals(asList("A1", "A2"), actual);
	}
	
	@Test
	public void whenA1B1B2AvailableSeats_get2AvailableSeatsInSameCoach_returnsB1B2() {
		Train train = aTrain("A1","B1","B2");
		List<String> actual = train.getAvailableSeatsInSameCoach(2);
		assertEquals(asList("B1", "B2"), actual);
	}
	
	private static Train aTrain(String... seatsStr) {
		List<SeatData> seats = new ArrayList<>();
		for (String seatStr : seatsStr) {
			boolean reserved = seatStr.endsWith("*"); 
			seats.add(new SeatData(seatStr.substring(0,1), seatStr.substring(1,2), reserved?"r":null));
		}
		Map<String, SeatData> seatsMap = seats.stream()
				.collect(toMap(SeatData::getId, s -> s));
		return new Train(seatsMap);
	}
	
	private static SeatData availableSeat(String coach, String seatNumber) {
		return new SeatData(coach, seatNumber, null);
	}
	private static SeatData reservedSeat(String coach, String seatNumber) {
		return new SeatData(coach, seatNumber, "r");
	}
	

}
