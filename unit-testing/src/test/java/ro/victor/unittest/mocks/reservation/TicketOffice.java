package ro.victor.unittest.mocks.reservation;

import static java.util.Collections.emptyList;

import java.util.List;

public class TicketOffice {

	private TrainDataService dataService;
	private ReservationCodeService codeService;
	
	public Reservation reserve(String trainId, int wantedSeats) {
		Train train = dataService.getTrainData(trainId);
		if (train.getTotalBookedSeats() + wantedSeats > 0.7 * train.getTotalSeats()) {
			return new Reservation(trainId, null, emptyList());
		} 
		List<String> seatIds = train.getAvailableSeatsInSameCoach(wantedSeats);
		if (seatIds.isEmpty()) {
			return new Reservation(trainId, null, emptyList());
		}
		String bookingReference = codeService.generateCode();
		dataService.reserve(trainId, seatIds, bookingReference);
		return new Reservation(trainId, bookingReference, seatIds);
	}

}
