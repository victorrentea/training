package ro.victor.unittest.legacy.tripservice.service;
// Original by Sandro Mancuso: https://www.youtube.com/watch?v=_NnElPO5BU0

import ro.victor.unittest.legacy.tripservice.exception.UserNotLoggedInException;
import ro.victor.unittest.legacy.tripservice.model.Trip;
import ro.victor.unittest.legacy.tripservice.model.User;

import java.util.ArrayList;
import java.util.List;

public class TripService {
	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		List<Trip> tripList = new ArrayList<>();
		User loggedUser = UserSession.getInstance().getLoggedUser();
		boolean isFriend = false;
		if (loggedUser !=null) {
			for (User friend : user.getFriends()) {
				if (friend.equals(loggedUser)) {
					isFriend = true;
					break;
				}
			}
			if (isFriend) {
				tripList = TripDAO.findTripsByUser(user);
			}
			return tripList;
		} else {
			throw new UserNotLoggedInException();
		}
	}
}
