package victor.training.java8.voxxed.order.repo;

import victor.training.java8.voxxed.order.entity.OrderLine;

public interface OrderLineRepository {

	void delete(OrderLine line);

	void insert(OrderLine liveLine);

	void update(OrderLine line);

}
