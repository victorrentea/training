package batch.clean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import org.hibernate.util.EqualsHelper;

import batch.model.domain.Appointment;
import batch.model.domain.Box;
import batch.model.domain.BoxType;
import batch.model.domain.Consumer;
import batch.model.domain.GoodCategory;
import batch.model.domain.Merchant;
import batch.model.domain.PaymentStatus;
import batch.model.domain.ServicePlatform;
import batch.model.domain.Shipment;
import batch.model.domain.Shop;
import batch.model.domain.TimeRange;
import batch.model.upload.BatchOrder;
import batch.model.upload.BatchOrderLine;
import batch.model.upload.BatchUpload;
import batch.services.ConsumerService;
import batch.services.PlatformService;
import batch.util.CurrencyFormatter;
import batch.util.DateUtils;

public class BatchOrderProcessor {
	private final Merchant merchant;
	private final Date appointmentDate;
	private final BatchOrder order;
	
	private ConsumerService consumerService;
	private PlatformService platformService;
	
	private Consumer consumer;
	private ServicePlatform consumerPlatform;
	
	private Appointment appointment;
	private Shipment shipment;
	
	public BatchOrderProcessor(BatchUpload uploadRequest, BatchOrder batchOrder) {
		this.merchant = uploadRequest.getMerchant();
		this.appointmentDate = DateUtils.truncateTimeStamp(uploadRequest.getAppointmentDate());
		this.order = batchOrder;
	}
	
	@SuppressWarnings("serial")
	public static class InvalidOrder extends RuntimeException {
		public InvalidOrder(String message) {
			super(message);
		}
	}
	
	public Shipment execute() {
		validateOrder();
		lookupConsumer();
		lookupConsumerPlatform();
		
		if (consumer == null) {
			createNewConsumer();
		} else {
			updateExistingConsumer();
			retrieveExistingAppointment();
		}

		if (appointment == null) {
			registerNewAppointment();
		}

		createShipment();
		if (orderHasBoxes()) {
			createBoxesForShipment();
		}

		order.setShipment(shipment); 			
		return shipment;
	}


	private void retrieveExistingAppointment() {
		appointment = consumerService.getConsumerAppointmentAtDate(consumer.getId(), appointmentDate);
	}
	

	private void validateOrder() {
		validateAllLinesAreValid();
		validateLinesHaveIdenticalConsumerInformation();
		validateLinesOfConsolidatedOrderHaveBoxTypeAndGoodsType();
		validateLinesHaveTheSamePrice();
	}

	private void validateAllLinesAreValid() {
		for (BatchOrderLine line : order.getLines()) {
			if (line.hasErrors()) {
				throw new InvalidOrder("Line(s) of this order are invalid");
			}
		}
	}

	private void validateLinesHaveIdenticalConsumerInformation() {
		BatchOrderLine firstLine = order.getLines().iterator().next();
		for (BatchOrderLine line : order.getLines()) {
			if (!Arrays.equals(firstLine.getConsumerFields(), line.getConsumerFields())) {
				throw new InvalidOrder("Lines of this order have different consumer information");
			}
		}
	}

	private void validateLinesOfConsolidatedOrderHaveBoxTypeAndGoodsType() {
		if (order.getLines().size() > 1) {
			for (BatchOrderLine line : order.getLines()) {
				if (line.getBoxType() == null || line.getGoodsType() == null) {
					throw new InvalidOrder("Line(s) of this order have empty BoxType or GoodsType");
				}
			}
		}
	}
	
	private void validateLinesHaveTheSamePrice() {
		BatchOrderLine firstLine = order.getLines().iterator().next();
		for (BatchOrderLine line : order.getLines()) {
			if (!EqualsHelper.equals(firstLine.getPrice(), line.getPrice())) {
				throw new InvalidOrder("Lines of this order have different prices");
			}
		}
	}
	
	private void lookupConsumer() {
		consumer = consumerService.getConsumerByEmail(order.getEmail());
		if (consumer != null && !consumer.isActivated()) {
			throw new InvalidOrder("The consumer is inactive");
		}
	}
	
	private void lookupConsumerPlatform() {
		consumerPlatform =  platformService.getPlatformForPostalCode(order.getPostalCode());
		if (consumerPlatform == null) {
			throw new InvalidOrder("The consumer postal code doesn't match any Platform");
		} 
	}
	
	private void createNewConsumer() {
		consumer = new Consumer();
		consumer.setFirstName(order.getFirstName());
		consumer.setLastName(order.getLastName());
		consumer.setPhoneNumber(order.getPhoneNumber());
		consumer.setEmailAddress(order.getEmail());
		fillConsumerAddress();
		consumerService.createConsumer(consumer);
	}

	private void updateExistingConsumer() {
		consumer.setFirstName(order.getFirstName());
		consumer.setLastName(order.getLastName());
		consumer.setPhoneNumber(order.getPhoneNumber());
		fillConsumerAddress();
	}
	
	private void fillConsumerAddress() {
		consumer.getAddress().setStreet(order.getStreet());
		consumer.getAddress().setNumber(order.getStreetNr());
		consumer.getAddress().setCity(order.getTown());
		consumer.getAddress().setPostalCode(order.getPostalCode());
		consumer.getAddress().setBox(order.getFloor());
	}

	private void registerNewAppointment() {
		TimeRange serviceSlot = consumerPlatform.getServiceSlots().first();
        
		appointment = consumerService.createAppointmentOnServiceSlot(consumer, appointmentDate, serviceSlot);
		if (appointment == null) {
			throw new RuntimeException("Could not create appointment !");
		}
	}

	private void createShipment() {
		shipment = new Shipment(getShopForConsumerPlatform(), consumer);
		shipment.setAppointment(appointment);
		shipment.setReferenceNumber(order.getOrderReference());
		shipment.setDescription(order.getDescription());
		fillPaymentDetails();
	}

	private void fillPaymentDetails() {
		BigDecimal price = CurrencyFormatter.parse(order.getPrice());
		if (price != null && BigDecimal.ZERO.compareTo(price) != 0) {
			shipment.setPaymentStatus(PaymentStatus.CREDITCARD_OPEN);
			shipment.setPrice(price);
		} else {
			shipment.setPaymentStatus(PaymentStatus.NOT_APPLICABLE);
		}
	}

	private Shop getShopForConsumerPlatform() {
		Shop shop = merchant.getShopForPlatform(consumerPlatform);
		if (shop == null) {
			throw new InvalidOrder("There are no shops available for the consumer platform");
		}
		return shop;
	}

	private boolean orderHasBoxes() {
		BatchOrderLine firstLine = order.getLines().get(0);
		return order.getLines().size() > 1 || 
			(firstLine.getGoodsType() != null && firstLine.getBoxType() != null);
	}
	
	private void createBoxesForShipment() {
		for (BatchOrderLine line : order.getLines()) {
			Box box = new Box();
			line.setBox(box);
			box.setType(BoxType.valueOf(line.getBoxType()));
			box.setGoodCategory(GoodCategory.valueOf(line.getGoodsType()));
			shipment.addBox(box);
		}
	}

	public void setConsumerService(ConsumerService consumerService) {
		this.consumerService = consumerService;
	}
	
	public void setPlatformService(PlatformService platformService) {
		this.platformService = platformService;
	}
	
}
