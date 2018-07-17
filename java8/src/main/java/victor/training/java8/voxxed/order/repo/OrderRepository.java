package victor.training.java8.voxxed.order.repo;

import victor.training.java8.voxxed.order.entity.Order;

public interface OrderRepository {
	void save(Order order);
}
