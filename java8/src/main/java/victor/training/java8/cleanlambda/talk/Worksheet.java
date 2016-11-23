package victor.training.java8.cleanlambda.talk;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javax.swing.JButton;

import victor.training.java8.cleanlambda.talk.Order.Status;

public class Worksheet {
	private final static Logger log = null;
	
	private static Predicate<Order> withStatus(Order.Status status) {
		return order -> order.isActive();
	}
	
	private static Predicate<Order> withFutureDeliveryDate() {
		return order -> order.getDeliveryDueDate().after(new Date());
	}

	@SuppressWarnings({ "unused", "null" })
	private void run() throws FileNotFoundException {

		List<Order> orders = Arrays.asList(new Order(), new Order(), new Order());
		
		orders.stream()
			.filter(order -> order.getStatus() == Status.ACTIVE)
			.filter(order -> order.isActive())
			.filter(Order::isActive)
			.findFirst()
				;
		
		orders.stream()
			.filter(withStatus(Status.ACTIVE).and(withFutureDeliveryDate()))
			.filter(this::isActiveAndInFuture)
			.findFirst();
		
		
		List<OrderDto> dtos = orders.stream()
			.map(entity -> {
				OrderDto dto = new OrderDto();
				dto.setId(entity.getId());
				dto.setDeliveryDate(entity.getDeliveryDueDate());
				return dto;
			})
			.collect(toList());
		
		
		
		Map<Customer, List<Order>> map = orders.stream()
			.collect(groupingBy(Order::getCustomer));
		
		
		helper.execute(() -> {/* do stuff in transaction */});
		
		JButton button = null;
		
		button.addActionListener(click -> {handleClick();});
		
		List<Order> newList = orders.stream()
			.sorted(Comparator.comparing(Order::getDeliveryDueDate))
			.collect(toList());
		
		for (Order order : orders) {
			log.info(order.toString());
		}
		
		orders.forEach(order -> {
			log.info(order.toString());
		});
		
		orders.stream()
			.filter(order -> order.getOrderLines().stream()
						.anyMatch(orderLine -> orderLine.getQuantity() > 10))
			.collect(toList());
	
//		Function.identity()
		
		List<OrderLine> promoLines = orders.stream()
			.filter(Order::isActive)
			.flatMap(order -> order.getOrderLines().stream())
			.filter(OrderLine::isPromo)
			.collect(toList());
		
		
		for (Order order : orders) {
			if (order.isActive()) {
				promoLines.addAll(order.getOrderLines().stream()
						.filter(OrderLine::isPromo)
						.collect(toList()));
			}
		}
		
		List<Email> emails = new ArrayList<>();
		
		
		List<CompletableFuture<Void>> futures = new ArrayList<>();
		for (Email email : emails) {
			futures.add(CompletableFuture.runAsync(() -> sendEmail(email)));
		}
//		CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).then
		
		OutputStream os = new FileOutputStream(new File(""));
//		Consumer<Integer> byteWriter = os::write;
		
		List<Integer> integers = null;
		
//		OutputStream os = new FileOutputStream(new File(""));
//		Consumer<Integer> byteWriter =  
		
		int sum = integers.stream()
			.mapToInt(i -> i)
			.sum();
			
	}
	
	public interface ConsumerWithException<T> {
		 void accept(T t) throws Exception;
	}
	private <T> Consumer<T> wrapException(ConsumerWithException<T> unsafeConsumer) {
		return t -> {
			try {
				unsafeConsumer.accept(t);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}
	
	private void sendEmail(Email email) {
	}

	private void handleClick() {
		// TODO Auto-generated method stub
		
	}
	ExecuteInTransactionHelper helper;
	
	private boolean isActiveAndInFuture(Order order) {
		return order.isActive() && order.getDeliveryDueDate().after(new Date());
	}
	
	
	public static Date tomorrow() {
		return null;
	}
	
//	void m(Date warningDate){
//		List<Order> orders = new ArrayList<>();
//		
//		Set<Customer> customersToNotify = orders.stream()
//			.filter(order -> order.getDeliveryDueDate().before(warningDate) && 
//							 order.getOrderLines().stream()
//							 	.anyMatch(line -> line.getStatus() != OrderLine.Status.IN_STOCK))
//			.map(Order::getCustomer)
//			.collect(toSet());
//		
//		
//		orders.stream()
//			.filter(order -> order.getDeliveryDueDate().before(warningDate))
//			.filter(order -> order.getOrderLines().stream()
//								.anyMatch(line -> line.getStatus() != OrderLine.Status.IN_STOCK))
//			.map(Order::getCustomer)
//			.collect(toSet());
//		
//		
//		orders.stream()
//			.filter(order -> order.getDeliveryDueDate().before(warningDate))
//			.filter(order -> order.getOrderLines().stream().anyMatch(OrderLine::isNotInStock))
//			.map(Order::getCustomer)
//			.collect(toSet());
//		
//		
//		orders.stream()
//			.filter(OrderPredicates.deliveryDueBefore(warningDate).or(this::needsTracking))
//			.filter(this::hasOrderLinesNotInStock)
//			.map(Order::getCustomer)
//			.collect(toSet());
//		
//		
//		// inca unul cu Optional  + maph din alta clas
//	}
//	

	public boolean needsTracking(Order order) {
		return true;
	}
	
}
