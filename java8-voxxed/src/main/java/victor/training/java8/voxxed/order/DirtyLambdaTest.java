package victor.training.java8.voxxed.order;

import static java.time.LocalDate.now;
import static java.util.Collections.singleton;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import victor.training.java8.voxxed.order.dto.AuditDto;
import victor.training.java8.voxxed.order.entity.Audit;
import victor.training.java8.voxxed.order.entity.Audit.Action;
import victor.training.java8.voxxed.order.entity.Customer;
import victor.training.java8.voxxed.order.entity.Order;
import victor.training.java8.voxxed.order.entity.OrderLine;
import victor.training.java8.voxxed.order.entity.OrderLine.Status;
import victor.training.java8.voxxed.order.entity.Product;
import victor.training.java8.voxxed.order.repo.OrderLineRepository;

@RunWith(MockitoJUnitRunner.class)
public class DirtyLambdaTest {

	@Mock
	private OrderLineRepository repo;

	@InjectMocks
	private DirtyLambdas target;
	
	private LocalDate today = LocalDate.now();
	private LocalDate yesterday = LocalDate.now().minusDays(1);
	
	@Test
	public void toDtos() {
		Audit audit1 = new Audit().setDate(yesterday).setAction(Action.ACTIVATE).setUser("jdoe");
		Audit audit2 = new Audit().setDate(today).setAction(Action.MODIFY).setUser("janedoe");
		Audit audit3 = new Audit().setDate(today).setAction(Action.MODIFY).setUser("janedoe");
		List<Audit> audits = Arrays.asList(audit1, audit2, audit3);
		List<AuditDto> dtos = new ArrayList<>(target.toDtos(audits));
		assertEquals("Number of DTOs", 2, dtos.size());
		assertEquals("janedoe", dtos.get(0).username);
		assertEquals(today, dtos.get(0).date);
		assertEquals(Action.MODIFY, dtos.get(0).action);
		assertEquals("jdoe", dtos.get(1).username);
		assertEquals(yesterday, dtos.get(1).date);
		assertEquals(Action.ACTIVATE, dtos.get(1).action);
	}
	
	@Test
	public void getCustomersToNotifyOfOverdueOrders() {
		Customer c1 = new Customer(), c2 = new Customer(), c3 = new Customer();
		LocalDate warningDate = now().plusDays(7);
		List<Order> orders = Arrays.asList(
			new Order(new OrderLine(Status.IN_STOCK))
				.setCustomer(c1)
				.setDeliveryDueDate(now().plusDays(2)),
			new Order(new OrderLine(Status.OUT_OF_STOCK))
				.setCustomer(c2)
				.setDeliveryDueDate(now().plusDays(10)),
			new Order(new OrderLine(Status.OUT_OF_STOCK))
				.setCustomer(c3)
				.setDeliveryDueDate(now().plusDays(2)));
		Set<Customer> actual = target.getCustomersToNotifyOfOverdueOrders(orders, warningDate);
		
		assertEquals(singleton(c3), actual);
	}
	
	@Test
	public void updateOrderLines() {
		Product p1 = new Product("p1"), p2 = new Product("p2"), p3 = new Product("p3");
		Order oldOrder = new Order(new OrderLine(p1, 1), new OrderLine(p2, 1));
		Order newOrder = new Order(new OrderLine(p2, 2), new OrderLine(p3, 1));
		target.updateOrderLines(oldOrder, newOrder);
		verify(repo).delete(Mockito.argThat(matcher(line -> line.getProduct() == p1)));
		verify(repo).update(Mockito.argThat(matcher(line -> line.getProduct() == p2 && line.getItems() == 2)));
		verify(repo).insert(Mockito.argThat(matcher(line -> line.getProduct() == p3)));
	}
	
	private static <T> Matcher<T> matcher(Predicate<T> test) {
		return new BaseMatcher<T>() {
			@Override
			public boolean matches(Object item) {
				return test.test((T) item);
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("Predicate test failed");
			}
		};
	}
	
	
	@Test
	public void getProductsSortedByHits() {
		Product p1 = new Product("p1"), p2 = new Product("p2"), p3 = new Product("p3"), p4 = new Product("p4");
		List<Order> orders = Arrays.asList(
			new Order(new OrderLine(p1, 200)) // this is not counted
				.setStatus(Order.Status.INACTIVE)
				.setDeliveryDueDate(now().minusWeeks(2)),
			new Order(new OrderLine(p2, 50), new OrderLine(p4, 100))
				.setStatus(Order.Status.ACTIVE)
				.setDeliveryDueDate(now()),
			new Order(new OrderLine(p2, 50), new OrderLine(p3,70))
				.setStatus(Order.Status.INACTIVE)
				.setDeliveryDueDate(now()));
		
		List<Product> expected = Arrays.asList(p2, p4, p3);
		List<Product> actual = target.getProductsSortedByHits(orders);
		assertEquals(expected, actual);
	}
}
