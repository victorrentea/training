package tripservice.service;

import java.util.List;

import tripservice.exception.ShouldBeMockedAwayException;
import tripservice.model.Trip;
import tripservice.model.User;

public class TripDAO {

	public static List<Trip> findTripsByUser(User user) {
		throw new ShouldBeMockedAwayException("Depends on infra stuff");
	}

}
