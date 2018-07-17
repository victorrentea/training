package victor.training.java8.stream.order.repo;

import victor.training.java8.stream.order.entity.Order;

public interface OrderRepository {
	void save(Order order);
}
