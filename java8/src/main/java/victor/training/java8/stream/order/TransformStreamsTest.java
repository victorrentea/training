package victor.training.java8.stream.order;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import victor.training.java8.stream.order.dto.OrderDto;
import victor.training.java8.stream.order.entity.Customer;
import victor.training.java8.stream.order.entity.Order;
import victor.training.java8.stream.order.entity.OrderLine;
import victor.training.java8.stream.order.entity.Product;
import victor.training.java8.stream.order.entity.Order.PaymentMethod;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransformStreamsTest {
	
	private TransformStreams service = new TransformStreams();
	
	private LocalDate today = LocalDate.now();
	private LocalDate yesterday = LocalDate.now().minusDays(1);
	
	@Test
	public void p01_toDtos() {
		Order order1 = new Order().setCreationDate(today).setTotalPrice(BigDecimal.TEN);
		Order order2 = new Order().setCreationDate(yesterday).setTotalPrice(BigDecimal.ONE);
		
		List<OrderDto> dtos = service.p01_toDtos(Arrays.asList(order1, order2));
		
		assertEquals(today, dtos.get(0).creationDate);
		assertEquals(BigDecimal.TEN, dtos.get(0).totalPrice);
		assertEquals(yesterday, dtos.get(1).creationDate);
	}
	
	@Test
	public void p02_getUsedPaymentMethods() {
		Order cardOrder = new Order().setPaymentMethod(PaymentMethod.CARD);
		Order cardOrder2 = new Order().setPaymentMethod(PaymentMethod.CARD);
		Order cashOnDeliveryOrder = new Order().setPaymentMethod(PaymentMethod.CASH_ON_DELIVERY);
		
		HashSet<PaymentMethod> expected = new HashSet<>(Arrays.asList(PaymentMethod.CARD, PaymentMethod.CASH_ON_DELIVERY));
		assertEquals(expected, service.p02_getUsedPaymentMethods(new Customer(cardOrder, cardOrder2, cashOnDeliveryOrder)));
	}
	
	@Test
	public void p03_getOrderDatesAscending() {
		Order order1 = new Order().setCreationDate(today);
		Order order1bis = new Order().setCreationDate(today);
		Order order2 = new Order().setCreationDate(yesterday);
		
		List<LocalDate> expected = Arrays.asList(yesterday, today);
		Collection<LocalDate> actual = service.p03_getOrderDatesAscending(new Customer(order1, order1bis, order2));
		
		assertEquals(expected, new ArrayList<>(actual));
	}
	
	@Test
	public void p04_mapOrdersById() {
		Order order1 = new Order(1L);
		
		Map<Long, Order> actual = service.p04_mapOrdersById(new Customer(order1));
		Map<Long, Order> expected = Collections.singletonMap(1L, order1);
		assertEquals(expected, actual);
	}
	
	
	@Test
	public void p05_getProductsByPaymentMethod() {
		Order order1 = new Order().setPaymentMethod(PaymentMethod.CARD);
		Order order2 = new Order().setPaymentMethod(PaymentMethod.CASH_ON_DELIVERY);
		Order order3 = new Order().setPaymentMethod(PaymentMethod.CARD);
		Map<PaymentMethod, List<Order>> actual = service.p05_getProductsByPaymentMethod(new Customer(order1, order2, order3));
		assertEquals(Arrays.asList(order2), actual.get(PaymentMethod.CASH_ON_DELIVERY));
		assertEquals(Arrays.asList(order1, order3), actual.get(PaymentMethod.CARD));
	}
	
	
	@Test
	public void p06_getProductCount() {
		Product chair = new Product("Chair");
		Product table = new Product("Table");
		
		Order order1 = new Order(
				new OrderLine(chair, 3));
		Order order2 = new Order(
				new OrderLine(table, 1),
				new OrderLine(chair, 1));
		
		Map<Product, Long> actual = service.p06_getProductCount(new Customer(order1, order2));
		Map<Product, Long> expected = new HashMap<Product, Long>(){{
			put(chair, 4L);
			put(table, 1L);
		}};
		assertEquals(expected, actual);
	}
	
	
	@Test
	public void p07_getAllOrderedProducts() {
		Product chair = new Product("Chair");
		Product table = new Product("Table");
		
		Order order1 = new Order(
				new OrderLine(chair, 3));
		Order order2 = new Order(
				new OrderLine(table, 1),
				new OrderLine(chair, 1));
		
		List<Product> actual = service.p07_getAllOrderedProducts(new Customer(order1, order2));
		assertEquals(Arrays.asList(chair, table), actual);
	}
	
	@Test
	public void p08_getProductsNamesJoined() {
		Product armchair = new Product("Armchair");
		Product chair = new Product("Chair");
		Product table = new Product("Table");
		
		Order order1 = new Order(
				new OrderLine(chair, 3));
		Order order2 = new Order(
				new OrderLine(armchair, 1),
				new OrderLine(table, 1),
				new OrderLine(chair, 1));
		
		String actual = service.p08_getProductsJoined(new Customer(order1, order2));
		assertEquals("Armchair,Chair,Table", actual);
	}
	
	
	@Test
	public void p09_getApproximateTotalOrdersPrice() {
		Order order1 = new Order().setTotalPrice(BigDecimal.TEN);
		Order order2 = new Order().setTotalPrice(BigDecimal.ONE);
		
		long actual = service.p09_getApproximateTotalOrdersPrice(new Customer(order1, order2));
		assertEquals(11L, actual);
	}
	
	@Test
	public void p10_readOrderFromFile() throws IOException {
		List<OrderLine> orderLines = service.p10_readOrderFromFile(new File("test.ok.txt"));
		assertEquals("Chair", orderLines.get(0).getProduct().getName());
		assertEquals(2, orderLines.get(0).getCount());
		assertEquals("Table", orderLines.get(1).getProduct().getName());
		assertEquals(1, orderLines.get(1).getCount());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void p10_readOrderFromFile_throws() throws IOException {
		service.p10_readOrderFromFile(new File("test.invalid.txt")); // look at stacktrace
		// TODO uncomment to see the exception trace :S
	}
}
