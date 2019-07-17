package ro.victor.unittest.legacy.tripservice.service;


import ro.victor.unittest.legacy.tripservice.exception.ShouldBeMockedAwayException;
import ro.victor.unittest.legacy.tripservice.model.Trip;
import ro.victor.unittest.legacy.tripservice.model.User;

import java.util.List;

public class TripDAO {

	public static List<Trip> findTripsByUser(User user) {
		throw new ShouldBeMockedAwayException("Depends on infra stuff");
	}

}
