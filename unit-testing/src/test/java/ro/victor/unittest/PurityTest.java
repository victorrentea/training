package ro.victor.unittest;



class Purity {
	private final UserRepository userRepo;
	private final OrderRepository orderRepo;
	private Purity(UserRepository userRepo, OrderRepository orderRepo) {
		this.userRepo = userRepo;
		this.orderRepo = orderRepo;
	}
	
	public long placeOrder(long userId, Cart cart) {
		User user = userRepo.findById(userId);
		Order order = createOrder(user, cart);
		orderRepo.save(order);
		return order.getId();
	}

	Order createOrder(User user, Cart cart) {
		// heavy logic
		Order order = new Order();
		order.setDeliveryCountry(
				user.getAddress().getCountry());
		// more heavy logic
		return order;
	}
	
}

class Cart {}
class Country {}
class Address {
	public Country getCountry() { return null; }
}
class User {
	public Address getAddress() { return null; }
}
interface UserRepository {
	User findById(long userId);
}
class Order {
	public long getId() { return 0; }
	public void setDeliveryCountry(Country country) {}
}
interface OrderRepository {
	void save(Order order);
}

public class PurityTest {
	
}