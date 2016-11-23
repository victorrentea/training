package victor.training.java8.cleanlambda.talk;

import java.util.Date;
import java.util.function.Predicate;

public class OrderPredicates {
	
	public static Predicate<Order> deliveryDueBefore(Date date) {
		return order -> order.getDeliveryDueDate().before(date);
	}
}
