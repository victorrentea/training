package ro.victor.unittest.mocks.reservation;

import org.apache.commons.lang3.StringUtils;

public class SeatData {
	private final String bookingReference;
	private final String seatNumber;
	private final String coach;
	public SeatData(String coach, String seatNumber, String bookingReference) {
		this.bookingReference = bookingReference;
		this.seatNumber = seatNumber;
		this.coach = coach;
	}
	public String getBookingReference() {
		return bookingReference;
	}
	public String getSeatNumber() {
		return seatNumber;
	}
	public String getCoach() {
		return coach;
	}
	
	public boolean isBooked() {
		return StringUtils.isNotBlank(bookingReference);
	}
	
	public String getId() {
		return coach + seatNumber;
	}
	
	
	
}
