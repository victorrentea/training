package victor.training.java8.voxxed.order;

import static java.time.LocalDate.now;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static victor.training.java8.voxxed.order.CleanLambdas.OrderPredicates.deliveryDueAfter;
import static victor.training.java8.voxxed.order.CleanLambdas.OrderPredicates.deliveryDueBefore;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import victor.training.java8.voxxed.order.dto.AuditDto;
import victor.training.java8.voxxed.order.entity.Audit;
import victor.training.java8.voxxed.order.entity.Customer;
import victor.training.java8.voxxed.order.entity.Order;
import victor.training.java8.voxxed.order.entity.Order.Status;
import victor.training.java8.voxxed.order.entity.OrderLine;
import victor.training.java8.voxxed.order.entity.Product;
import victor.training.java8.voxxed.order.repo.OrderLineRepository;

/*
 * Some code is commented out as it requires changes to the data model. 
 * The goal is to work our way to this code together. 
 * */
public class CleanLambdas {
	
	private OrderLineRepository repo;
	
	public Collection<AuditDto> toDtos(List<Audit> audits) { 
//		Comparator<AuditDto> comparator = comparing(AuditDto::getDate).reversed()
//						.thenComparing(comparing(AuditDto::getAction))
//						.thenComparing(comparing(AuditDto::getUsername));
//		return audits.stream()
//				.map(AuditDto::new)
//				.toCollection(() -> new TreeSet<>(comparator));
		return null;
	}
	
	public static class OrderPredicates {// would go global

		public static Predicate<Order> deliveryDueBefore(LocalDate date) {
			return order -> order.getDeliveryDueDate().isBefore(date);
		} 
		
		public static Predicate<Order> deliveryDueAfter(LocalDate date) {
			return order -> order.getDeliveryDueDate().isAfter(date);
		} 
		
	}
	
	public Set<Customer> getCustomersToNotifyOfOverdueOrders(List<Order> orders, LocalDate warningDate) {
		return orders.stream()
			.filter(deliveryDueBefore(warningDate))
			.filter(this::hasOrderLinesNotInStock)
			.map(Order::getCustomer)
			.collect(toSet());
	}
	
	private boolean hasOrderLinesNotInStock(Order order) {
		return order.getOrderLines().stream()
			 	.anyMatch(line -> line.getStatus() != OrderLine.Status.IN_STOCK);
		//	 	.anyMatch(OrderLine::notInStock); 
	}
	
	
	public void updateOrderLines(Order oldOrder, Order newOrder) {
		// delete unused old lines
		Set<Product> newProducts = newOrder.getOrderLines().stream().map(OrderLine::getProduct).collect(toSet());
		oldOrder.getOrderLines().stream()
			.filter(oldLine -> !newProducts.contains(oldLine.getProduct()))
			.forEach(repo::delete);
		
		// insert new lines
		Set<Product> oldProducts = oldOrder.getOrderLines().stream().map(OrderLine::getProduct).collect(toSet());
		newOrder.getOrderLines().stream()
			.filter(newLine -> !oldProducts.contains(newLine.getProduct()))
			.forEach(repo::insert); // operatii pe seturi vs stream filter
		

		// update old lines
		Map<Product, Integer> productToNewCount = newOrder.getOrderLines().stream()
				.collect(toMap(OrderLine::getProduct, OrderLine::getCount));
		oldOrder.getOrderLines().stream()
			.filter(oldLine -> newProducts.contains(oldLine.getProduct()))
			.forEach(oldLine -> {
				oldLine.setCount(productToNewCount.get(oldLine.getProduct()));
				repo.update(oldLine);
			});
	}
	

	public List<Product> getProductsSortedByHits(List<Order> orders) {
		Map<Product, Integer> productHits = orders.stream()
				.filter(deliveryDueAfter(now().minusWeeks(1)).or(order -> order.getStatus() == Status.ACTIVE))
				.flatMap(order-> order.getOrderLines().stream())
				.sorted(comparing(orderLine -> orderLine.getProduct().getName()))
				.collect(groupingBy(OrderLine::getProduct, summingInt(OrderLine::getCount)));
		System.out.println("productHits: " + productHits);
		
		Map<Integer, List<Product>> hitsToProducts = productHits.entrySet().stream()
				.collect(groupingBy(Map.Entry::getValue, HashMap::new, 
							mapping(Map.Entry::getKey, toList())));
		System.out.println("hitsToProducts: " + hitsToProducts);
		
		return hitsToProducts.keySet().stream()
				.sorted(Comparator.reverseOrder())
				.map(hitsToProducts::get)
				.flatMap(products -> products.stream().sorted(comparing(Product::getName)))
				.collect(toList());
	}
	
	
}
