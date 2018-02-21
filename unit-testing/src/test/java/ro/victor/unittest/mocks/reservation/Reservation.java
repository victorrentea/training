package ro.victor.unittest.mocks.reservation;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Reservation {
	private final String trainId;
	private final String bookingReference;
	private final List<String> seats;
	public Reservation(String trainId, String bookingReference, List<String> seats) {
		this.trainId = trainId;
		this.bookingReference = bookingReference;
		this.seats = seats;
	}
	public String getTrainId() {
		return trainId;
	}
	
	private void m() {
		
	}
	public String getBookingReference() {
		return bookingReference;
	}
	public List<String> getSeats() {
		return seats;
	}
	
	@Override
	public boolean equals(Object arg0) {
		if (!(arg0 instanceof Reservation)) {
			throw new IllegalArgumentException();
		}
		Reservation other = (Reservation) arg0;
		return new EqualsBuilder()
				.append(this.trainId, other.trainId)
				.append(this.bookingReference, other.bookingReference)
				.append(this.seats, other.seats)
				.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(this.trainId)
				.append(this.bookingReference)
				.append(this.seats)
				.hashCode();
	}
	
	
	
}
