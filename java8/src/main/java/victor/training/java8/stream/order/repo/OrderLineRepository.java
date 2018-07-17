package victor.training.java8.stream.order.repo;

import victor.training.java8.stream.order.entity.OrderLine;

public interface OrderLineRepository {

	void delete(OrderLine line);

	void insert(OrderLine liveLine);

	void update(OrderLine line);

}
