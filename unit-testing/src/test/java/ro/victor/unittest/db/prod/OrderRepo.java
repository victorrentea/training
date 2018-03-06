package ro.victor.unittest.db.prod;

import java.util.Random;

import org.springframework.stereotype.Repository;

import ro.victor.unittest.mocks.prod.Order;

@Repository
public class OrderRepo {
	public int insertOrder(Order order) {
		// implementation ommited
		return new Random().nextInt();
	}
}
