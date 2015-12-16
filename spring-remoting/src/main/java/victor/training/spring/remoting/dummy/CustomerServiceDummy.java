package victor.training.spring.remoting.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class CustomerServiceDummy {

	private final Map<Long, Customer> store = new HashMap<Long, Customer>();
	
	@PostConstruct
	public void addDummyCustomers() {
		createCustomer(new Customer("John Doe", "0720123456", "jdoe@pentagon.org"));
		createCustomer(new Customer("Michael Feathers", "231312412", "mfeathers@mfeathers.com"));
		createCustomer(new Customer("Uncle Bob", "01231451232", "ubob@cleancoders.com"));
	}

	public synchronized Long createCustomer(Customer customer) {
		Long newId = nextId();
		customer.setId(newId);
		store.put(newId, customer);
		return newId;
	}

	private  Long nextId() {
		long lastId = -1;
		for (Customer customer : store.values()) {
			lastId = Math.max(lastId, customer.getId());
		}
		return lastId + 1;
	}

	public synchronized Customer findCustomer(Long id) {
		Customer customer = store.get(id);
		if (customer == null) {
			throw new NotFoundException();
		}
		return customer;
	}

	public synchronized List<Customer> searchCustomers(CustomerSearchCriteria criteria) {
		List<Customer> list = new ArrayList<Customer>();
		for (Customer customer : store.values()) {
			if (matches(customer, criteria)) {
				list.add(customer);
			}
		}
		return list;
	}

	private boolean matches(Customer customer, CustomerSearchCriteria criteria) {
		return stringContains(criteria.getName(), customer.getName()) &&
				stringContains(criteria.getPhone(), customer.getPhone()) &&
				stringContains(criteria.getEmail(), customer.getEmail());
	}

	private boolean stringContains(String part, String string) {
		return part == null || 
			string == null || 
			string.toUpperCase().contains(part.toUpperCase());
	}

	public synchronized void deleteCustomer(Long id) {
		if (store.get(id) == null) {
			throw new NotFoundException();
		}
		store.remove(id);
	}

	public Customer update(Long id, Customer customer) {
		if (store.get(id) == null) {
			throw new NotFoundException();
		}
		customer.setId(id);
		store.put(id, customer); // Tip: Avoid this "EntityManager.merge" in real applications
		return customer;
	}

}
