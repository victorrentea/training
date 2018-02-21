package ro.victor.unittest.mocks.reservation;

import java.util.List;

public interface TrainDataService {

	Train getTrainData(String trainId);

	void reserve(String trainId, List<String> seatIds, String bookingReference);
}
