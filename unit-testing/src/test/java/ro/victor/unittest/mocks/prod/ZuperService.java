package ro.victor.unittest.mocks.prod;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.victor.unittest.db.prod.NotificationRepo;
import ro.victor.unittest.db.prod.OrderRepo;
import ro.victor.unittest.db.prod.ReportingRepo;

// flagrant violation of SRP :p
@Service
public class ZuperService {

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private ReportingRepo reportingRepo;
	
	@Autowired
	private NotificationRepo notificationRepo;	
	
	public int placeOrder(Order order) {
		int orderId = orderRepo.insertOrder(order);
		String notificationText = "Order " + orderId + " placed at " + LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);
		notificationRepo.insertNotification(notificationText);
		return orderId;
	}
	
	public List<String> getAllDistinctNotifications() {
		return reportingRepo.getAllNotifications().stream()
			.distinct()
			.collect(toList());
	}
	
}
