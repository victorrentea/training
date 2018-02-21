package ro.victor.unittest.mocks.reservation;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TrainReservationServiceTest {
	
	@Mock
	private TrainDataService trainDataService;
	
	@Mock
	private ReservationCodeService codeService;
	
	@Mock
	private Train trainData;
	
	@InjectMocks
	private TicketOffice office;
	
	public static final Reservation EMPTY_RESERVATION = new Reservation("X", null, emptyList());;
	
	@Before
	public void setup() {
		when(codeService.generateCode()).thenReturn("AAA");
		when(trainDataService.getTrainData("X")).thenReturn(trainData);
		when(trainData.getTotalSeats()).thenReturn(100);
	}
	
	@Test
	public void reserve1SeatInTrainWithNoAvailableSeats_returnsBlankReservation() {
		when(trainData.getTotalBookedSeats()).thenReturn(100);
		Reservation reservation = office.reserve("X", 1);
		assertEquals(EMPTY_RESERVATION, reservation);
	}
	
	
	@Test
	public void reserve1SeatInTrainWith2SeatsAnd1Coach_returnsFirstSeat() {
		when(trainData.getTotalBookedSeats()).thenReturn(2);
		when(trainData.getAvailableSeatsInSameCoach(1)).thenReturn(asList("A1"));
		
		Reservation reservation = office.reserve("X", 1);
		
		Reservation expected = new Reservation("X", "AAA", singletonList("A1"));
		assertEquals(expected, reservation);
	}
	
	@Test
	public void reserve1SeatInTrainReaches70Percent_returnsReservation() {
		when(trainData.getTotalBookedSeats()).thenReturn(69);
		when(trainData.getAvailableSeatsInSameCoach(1)).thenReturn(asList("A1"));
		
		Reservation reservation = office.reserve("X", 1);
		
		Reservation expected = new Reservation("X", "AAA", singletonList("A1"));
		assertEquals(expected, reservation);
	}
	
	@Test
	public void reserve1SeatInTrainReaches71Percent_returnsEmptyReservation() {
		when(trainData.getTotalBookedSeats()).thenReturn(70);
		when(trainData.getAvailableSeatsInSameCoach(1)).thenReturn(asList("A1"));
		
		Reservation reservation = office.reserve("X", 1);
		
		assertEquals(EMPTY_RESERVATION, reservation);
	}
	

	@Test
	public void reserve10SeatsInEmptyTrainWithCoachesOf9Seats_returnsReservation() {
		when(trainData.getTotalBookedSeats()).thenReturn(0);
		when(trainData.getAvailableSeatsInSameCoach(10)).thenReturn(emptyList());
		
		Reservation reservation = office.reserve("X", 10);
		
		assertEquals(EMPTY_RESERVATION, reservation);
	}
	
	@Test
	public void reserve1SeatInEmptyTrain_callsReserveOnTrainDataService() {
		when(trainData.getTotalBookedSeats()).thenReturn(0);
		when(trainData.getAvailableSeatsInSameCoach(1)).thenReturn(asList("A1"));
		
		office.reserve("X", 1);
		
		verify(trainData).getAvailableSeatsInSameCoach(1);
		
		verify(trainDataService).reserve("X", singletonList("A1"), "AAA");
	}
	
}
