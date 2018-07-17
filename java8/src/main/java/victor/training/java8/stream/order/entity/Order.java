package victor.training.java8.stream.order.entity;

import static java.util.stream.Collectors.joining;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Order {

	public enum Status {
		DRAFT, ACTIVE, INACTIVE
	}
	
	public enum PaymentMethod {
		CARD,
		CASH_ON_SITE,
		CASH_ON_DELIVERY
	}
	
	private Long id;
	private Status status;
	private List<OrderLine> orderLines;
	private LocalDate creationDate;
	private BigDecimal totalPrice;
	private PaymentMethod paymentMethod;
	
	private LocalDate deliveryDueDate;
	private Customer customer;

	public Order() {
	}
	
	public Order(Status status) {
		this.status = status;
	}

	public Order(Long id) {
		this.id = id;
	}
	
	public Order(OrderLine... lines) {
		this.orderLines = Arrays.asList(lines);
	}

	// TODO any helper methods ?

	public List<OrderLine> getOrderLines() {
		return orderLines;
	}
	
	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public Order setStatus(Status status) {
		this.status = status;
		return this;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public Order setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
		return this;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public Order setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
		return this;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public Order setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
		return this;
	}

	public LocalDate getDeliveryDueDate() {
		return deliveryDueDate;
	}

	public Order setDeliveryDueDate(LocalDate deliveryDueDate) {
		this.deliveryDueDate = deliveryDueDate;
		return this;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Order setCustomer(Customer customer) {
		this.customer = customer;
		return this;
	}

	public String toString() {
		List<String> details = new ArrayList<>();
		if (creationDate != null) {
			details.add("created="+creationDate);
		}
		String detailsStr = details.stream().collect(joining(", "));
		return "Order{"+detailsStr + "}";
	}
	
}
