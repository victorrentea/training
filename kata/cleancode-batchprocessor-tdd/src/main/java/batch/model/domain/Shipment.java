package batch.model.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;

import batch.util.CurrencyFormatter;



public class Shipment extends BaseEntity {

	private Shop sender;
	private Consumer receiver;

	private String referenceNumber;

	private String description;

	private BigDecimal price;

	private PaymentStatus paymentStatus;

	private Appointment appointment;

	private Set<Box> boxes = new HashSet<Box>();

	protected Shipment() {
	}

	public Shipment(Shop sender, Consumer receiver) {
		this.sender = sender;
		this.receiver = receiver;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		if (price != null && !paymentStatus.isPriceNotMandatory()) {
			this.price = price.setScale(2, RoundingMode.HALF_UP);
		}
	}

	public String getPriceFormatted() {
		return CurrencyFormatter.formatWithTwoDecimals(getPrice());
	}

	public void setPriceFormatted(String priceFormatted) {
		setPrice(CurrencyFormatter.parse(priceFormatted));
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
		if (paymentStatus.isPriceNotMandatory() && paymentStatus != PaymentStatus.NOT_APPLICABLE) {
			setPrice(null);
		}
	}

	public Consumer getReceiver() {
		return receiver;
	}

	public void setReceiver(Consumer consumerReceiver) {
		this.receiver = consumerReceiver;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public String getConsumerReceiverName() {
		return getReceiver().getFullName();
	}

	public Address getConsumerReceiverAddress() {
		return getAppointment().getAppointmentRegistration().getAddress();
	}

	public Set<Box> getBoxes() {
		return boxes;
	}

	public void setBoxes(Set<Box> boxes) {
		this.boxes = boxes;
	}


	public void addBox(Box box) {
		box.setShipment(this);
		boxes.add(box);
	}

	public void removeBox(Box box) {
		box.setShipment(null);
		boxes.remove(box);
	}

	public void setSender(Shop shopSender) {
		this.sender = shopSender;
	}

	public Shop getSender() {
		return sender;
	}
	

}
