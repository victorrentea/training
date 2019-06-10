package tripservice.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import tripservice.exception.UserNotLoggedInException;
import tripservice.model.Trip;
import tripservice.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceShould {

  private User loggedUser = new User();
  private User user = new User();
  private List<Trip> userTripList = new ArrayList<>(Arrays.asList(new Trip()));

  @Mock
  private UserSession session;
  @Mock
  private TripDAO tripDao;

  @InjectMocks
  private TripService tripService;

  @Before
  public void setupMock() {
    when(session.getLoggedUser()).thenReturn(loggedUser);
    when(tripDao.findTripsByUser(any())).thenReturn(userTripList);
  }

  @Test(expected = UserNotLoggedInException.class)
  public void throwForNoSessionUser() {
    when(session.getLoggedUser()).thenReturn(null);
    tripService.getTripsByUser(user);
  }

  @Test
  public void returnEmptyListWhenSessionUserNotFriendWithAnyUserFriend() {
    user.addFriend(new User());
    List<Trip> tripList = tripService.getTripsByUser(user);
    assertEquals(emptyList(), tripList);
  }

  @Test
  public void returnEmptyListWhenUserWithNoFriends() {
    List<Trip> tripList = tripService.getTripsByUser(user);
    assertEquals(emptyList(), tripList);
  }

  @Test
  public void returnUserTripListWhenSessionUserFriendWithCurrentUser() {
    user.addFriend(loggedUser);
    List<Trip> tripList = tripService.getTripsByUser(user);
    assertEquals(userTripList, tripList);
  }
}
