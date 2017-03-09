package victor.training.java8.voxxed.order;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.summingLong;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

import org.jooq.lambda.Unchecked;

import victor.training.java8.voxxed.more.fake.Autowired;
import victor.training.java8.voxxed.order.dto.OrderDto;
import victor.training.java8.voxxed.order.entity.Customer;
import victor.training.java8.voxxed.order.entity.Order;
import victor.training.java8.voxxed.order.entity.Order.PaymentMethod;
import victor.training.java8.voxxed.order.entity.OrderLine;
import victor.training.java8.voxxed.order.entity.Product;

public class TransformStreams {

	/**
	 * Transform all entities to DTOs.
	 * Discussion:.. Make it cleanest!
	 */
	public List<OrderDto> p01_toDtos(List<Order> orders) {
		return orders.stream().map(orderMapper::toDto).collect(toList());		
	}
	
	@Autowired
	private OrderMapper orderMapper = new OrderMapper();

	public static class OrderMapper {
		private OrderDto toDto(Order order) {
			OrderDto dto = new OrderDto();
			dto.totalPrice = order.getTotalPrice(); 
			dto.creationDate = order.getCreationDate();
			return dto;
		}
	}
	
	
	/**
	 * Note: Order.getPaymentMethod()
	 */
	public Set<PaymentMethod> p02_getUsedPaymentMethods(Customer customer) {
		return customer.getOrders().stream()
				.map(Order::getPaymentMethod)
				.collect(toSet()); 
	}
	
	/**
	 * When did the customer created orders ?
	 * Note: Order.getCreationDate()
	 */
	public SortedSet<LocalDate> p03_getOrderDatesAscending(Customer customer) {
		return customer.getOrders().stream()
				.map(Order::getCreationDate)
				.collect(toCollection(TreeSet::new)); 
	}
	
	
	/**
	 * @return a map order.id -> order
	 */
	public Map<Long, Order> p04_mapOrdersById(Customer customer) {
		return customer.getOrders().stream()
				.collect(toMap(Order::getId, o->o)); 
	}
	
	/** 
	 * Orders grouped by payment methods
	 */
	public Map<PaymentMethod, List<Order>> p05_getProductsByPaymentMethod(Customer customer) {
		return customer.getOrders().stream()
				.collect(groupingBy(Order::getPaymentMethod)); 
	}
	
	// -------------- MOVIE BREAK :p --------------------
	
	/** 
	 * A hard one !
	 * Get total number of products bought by a customer, across all her orders.
	 * Customer --->* Order --->* OrderLines(.count .product)
	 * The sum of all counts for the same product.
	 * i.e. SELECT PROD_ID, SUM(COUNT) FROM PROD GROUPING BY PROD_ID
	 */
	public Map<Product, Long> p06_getProductCount(Customer customer) {
		return customer.getOrders().stream()
				.flatMap(order -> order.getOrderLines().stream())
				.collect(groupingBy(OrderLine::getProduct, summingLong(OrderLine::getCount)));
		
	}
	
	/**
	 * All the unique products bought by the customer, 
	 * sorted by Product.name.
	 */
	public List<Product> p07_getAllOrderedProducts(Customer customer) {
		return customer.getOrders().stream()
				.flatMap(order -> order.getOrderLines().stream()) // returns a stream of OrderLines
				.map(OrderLine::getProduct) // returns a Stream<Product>
				.distinct()
				.sorted(Comparator.comparing(Product::getName))
				.collect(toList()); 
	}
	
	
	/**
	 * The names of all the products bought by Customer,
	 * sorted and then concatenated by ",".
	 * Example: "Armchair,Chair,Table".
	 * Hint: Reuse the previous function.
	 */
	public String p08_getProductsJoined(Customer customer) {
		return p07_getAllOrderedProducts(customer).stream() // Stream<Product>
				.map(Product::getName)
				.collect(joining(",")); 
	}
	
	/**
	 * Sum of all Order.getTotalPrice(), truncated to Long.
	 */
	public Long p09_getApproximateTotalOrdersPrice(Customer customer) {
		return customer.getOrders().stream()
				.map(Order::getTotalPrice) // Stream<BigDecimal>
//				.mapToLong(BigDecimal::longValue) // LongStream
//				.sum()
//				.reduce(BigDecimal.ZERO, (bd1, bd2) -> bd1.add(bd2))
				.reduce(BigDecimal.ZERO, BigDecimal::add)
				.longValue()
				;  
	}
	
	// ----------- IO ---------------
	
	/**
	 * - Read lines from file as Strings. 
	 * - Where do you close the opened file?
	 * - Parse those to OrderLine using the function bellow
	 * - Validate the created OrderLine. Throw ? :S
	 */
	public List<OrderLine> p10_readOrderFromFile(File file) throws IOException {
		try (Stream<String> lines = Files.lines(file.toPath())) {
			return lines
				.map(line -> line.split(";")) // Stream<String[]>
				.filter(cell -> "LINE".equals(cell[0]))
				.map(this::parseOrderLine) // Stream<OrderLine>
				.peek(this::validateOrderLine)
				.collect(toList());
		}
	}
	
	private OrderLine parseOrderLine(String[] cells) {
		return new OrderLine(new Product(cells[1]), Integer.parseInt(cells[2]));
	}
	
	private void validateOrderLine(OrderLine orderLine) {
		if (orderLine.getCount() < 0) {
			throw new IllegalArgumentException("Negative items");			
		}
	}
	
	
	// TODO print cannonical paths of all files in current directory
	// use Unchecked... stuff
	
	public static void main(String[] args) {
		Stream.of(new File(".").listFiles())
//			.map(file -> {
//				try {
//					return file.getCanonicalPath();
//				} catch (Exception e) {
//					throw new RuntimeException(e);
//				}
//			})
			.map(Unchecked.function(File::getCanonicalPath))
			.forEach(System.out::println);
	}
}
