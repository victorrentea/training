package victor.training.java8.voxxed.order;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Stream;

import victor.training.java8.voxxed.order.dto.OrderDto;
import victor.training.java8.voxxed.order.entity.Customer;
import victor.training.java8.voxxed.order.entity.Order;
import victor.training.java8.voxxed.order.entity.Order.PaymentMethod;
import victor.training.java8.voxxed.order.entity.OrderLine;
import victor.training.java8.voxxed.order.entity.Product;

public class TransformStreams {

	/**
	 * #1
	 * Transform all entities to DTOs.
	 * Discussion:.. Make it cleanest!
	 */
	public List<OrderDto> toDtos(List<Order> orders) {
		// INITIAL(
		//List<OrderDto> dtos = new ArrayList<>();
		//for (Order order : orders) {
		//	OrderDto dto = new OrderDto();
		//	dto.totalPrice = order.getTotalPrice(); 
		//	dto.creationDate = order.getCreationDate();
		//	dtos.add(dto);
		//}
		//return dtos;
		// INITIAL)
		// SOLUTION(
		return orders.stream()
					.map(order -> {
						OrderDto dto = new OrderDto();
						dto.totalPrice = order.getTotalPrice(); 
						dto.creationDate = order.getCreationDate();
						return dto; 
					})
					.collect(toList());
		// SOLUTION)
	}
	
	/**
	 * #2
	 * What PaymentMethods has the Customer ever used ?
	 */
	public Set<PaymentMethod> getUsedPaymentMethods(Customer customer) {
		//return null; // INITIAL
		// SOLUTION(
		return customer.getOrders().stream()
					.map(Order::getPaymentMethod)
					.collect(toSet());
		// SOLUTION)
	}
	
	/**
	 * #3
	 * When did the customer created orders ?
	 */
	public SortedSet<LocalDate> getOrderDatesAscending(Customer customer) {
		//return null; // INITIAL
		// SOLUTION(
		return customer.getOrders().stream()
					.map(Order::getCreationDate)
					//.distinct().sorted().collect(toList())
					.collect(toCollection(TreeSet::new));
		// SOLUTION)
	}
	
	
	/**
	 * #4
	 * @return a map order.id -> order
	 */
	public Map<Long, Order> mapOrdersById(Customer customer) {
		//return null; // INITIAL
		// SOLUTION(
		return customer.getOrders().stream()
					.collect(toMap(Order::getId, Function.identity()));
		// SOLUTION)
	}
	
	/**
	 * #5
	 * All the unique products bought by the customer, 
	 * sorted by Product.name.
	 */
	public List<Product> getAllOrderedProducts(Customer customer) {
		//return null; // INITIAL
		// SOLUTION(
		return customer.getOrders().stream()
					.flatMap(order -> order.getOrderLines().stream())
					.map(OrderLine::getProduct)
					.distinct()
					.sorted(Comparator.comparing(Product::getName))
					.collect(toList());
		// SOLUTION)
	}
	
	
	// ------------- grouping --------------
	
	/** 
	 * #6
	 * No comment. Check signature.
	 */
	public Map<PaymentMethod, List<Order>> getProductsByPaymentMethod(Customer customer) {
		//return null; // INITIAL
		// SOLUTION(
		return customer.getOrders().stream()	
					.collect(groupingBy(Order::getPaymentMethod));
		// SOLUTION)
	}
	
	/** 
	 * #7
	 * Get total number of products ordered by a customer, 
	 * across all her orders.
	 */
	public Map<Product, Integer> getProductCount(Customer customer) {
		//return null; // INITIAL
		// SOLUTION(
		return customer.getOrders().stream()
					.flatMap(order -> order.getOrderLines().stream())
					.collect(groupingBy(OrderLine::getProduct, summingInt(OrderLine::getItems)));
		// SOLUTION)
	}
	
	// ---------- joining ----------
	
	/**
	 * #8
	 * The names of all the products ordered by Customer,
	 * sorted and then concatenated by ",".
	 * Example: "Armchair,Chair,Table".
	 * Hint: Try to reuse a function above.
	 */
	public String getProductsJoined(Customer customer) {
		//return null; // INITIAL
		// SOLUTION(
		return getAllOrderedProducts(customer).stream()
					.map(Product::getName)
					.sorted()
					.collect(joining(","));
		// SOLUTION)
	}
	
	/**
	 * #9
	 * Total money payed by the Customer. 
	 * Approximate to Long.
	 */
	public Long getApproximateTotalOrdersPrice(Customer customer) {
		//return null; // INITIAL
		// SOLUTION(
		return customer.getOrders().stream()
					.map(Order::getTotalPrice)
//						.map(BigDecimal::longValue)
//						.reduce(0l, Long::sum);
					.mapToLong(BigDecimal::longValue)
					.sum();
		// SOLUTION)
	}
	
	// ----------- IO ---------------
	
	/**
	 * #10
	 * - Read lines from file as Strings. 
	 * - Where do you close the opened file?
	 * - Parse those to OrderLine using the function bellow
	 * - Validate the created OrderLine. Throw ? :S
	 */
	public List<OrderLine> readOrderFromFile(File file) {
		//return null; // INITIAL
		// SOLUTION(
		try (Stream<String> lines = Files.lines(file.toPath())) {
			return lines.skip(2) // skip the header
				.map(line -> line.split(";"))
				.filter(cell -> "LINE".equals(cell[0]))
				.map(this::parseOrderLine)
				.peek(this::validateOrderLine) // vezi exceptia
				.collect(toList());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		// SOLUTION)
	}
	
	private OrderLine parseOrderLine(String[] cells) {
		return new OrderLine(new Product(cells[1]), Integer.parseInt(cells[2]));
	}
	
	private void validateOrderLine(OrderLine orderLine) {
		if (orderLine.getItems() < 0) {
			throw new IllegalArgumentException("Negative items");			
		}
	}
}
