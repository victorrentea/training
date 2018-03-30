package batch.dirty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.map.MultiValueMap;
import org.hibernate.util.EqualsHelper;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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



public class BatchUploadProcessor {

	private static final Logger log = LoggerFactory.getLogger(BatchUploadProcessor.class);
	private static final ClassValidator<BatchOrderLine> lineValidator = new ClassValidator<>(BatchOrderLine.class);

	private final BatchUpload uploadRequest;
	private final List<BatchOrderLine> initialOrderlines;
	
	private ConsumerService consumerService;
	private PlatformService platformService;
	private Set<String> merchantExistingOrderReferences;

	private List<BatchOrderLine> orderLines;
	private List<BatchOrder> orders = new ArrayList<>();
	private List<Shipment> shipments = new ArrayList<>();
	
	public BatchUploadProcessor(BatchUpload uploadRequest) {
		this.uploadRequest = uploadRequest;
		this.initialOrderlines = Collections.unmodifiableList(uploadRequest.getOrderLines());
		this.orderLines = new ArrayList<>(uploadRequest.getOrderLines());
		uploadRequest.setCreatedShipments(shipments);
	}

	public BatchUploadProcessor execute() {
		log.info("Start");
		checkNumberOfLines();
		createBoxesForOrderLines();
		checkConsistentConsumerDetailsByEmail();
		consolidateOrders();
		createShipmentsForBatchOrders();
		log.info("Finished");
		return this;
	}
	
	private void checkNumberOfLines() {
		if (orderLines.size() > 10000) {
			throw new IllegalArgumentException("The batch upload file has more than 10.000 lines. Please reduce the number of lines.");
		}
	}

	// TODO -------------- REFACTOR#1 START -----------------
	
	private void createBoxesForOrderLines() {
		for (BatchOrderLine line : orderLines) {
			// validate individual fields using Hibernate3 validator
			InvalidValue[] validationMessages = lineValidator.getInvalidValues(line);
			if (validationMessages.length > 0) {
				rejectOrderLine(line, Arrays.toString(validationMessages));
				continue;
			}
			
			// validate the order references are unique for this merchant
			if (line.getOrderReference() != null && 
				merchantExistingOrderReferences.contains(line.getOrderReference())) {
				rejectOrderLine(line, "This order reference was previously used for this merchant");
				continue;
			}

			// create box for order line
			Box box = new Box();
			if (line.getGoodsType() != null) {
				box.setGoodCategory(GoodCategory.valueOf(line.getGoodsType()));
			}
			if (line.getBoxType() != null)  {
				box.setType(BoxType.valueOf(line.getBoxType()));
			}
			line.setBox(box);

			// validate box type is allowed for merchant
			Set<BoxType> allowedBoxTypes = uploadRequest.getMerchant().getAllowedBoxTypes();
			if (box.getType() != null && !allowedBoxTypes.contains(box.getType())) {
				rejectOrderLine(line, "The Box type ('"+box.getType()+"') is not allowed for this merchant");
				continue;
			}
			
			
			// validate box type matches goods category within it
			if (box.getType() != null ^ box.getGoodCategory() != null) {
				rejectOrderLine(line, "The box type and good category are either BOTH specified, either NONE");
				continue;
			}
			if (box.getType()!=null && box.getGoodCategory()!=null) {
				Set<GoodCategory> allowedGoodCategories = GoodCategory.getGoodCategories(box.getType());
				if (!allowedGoodCategories.contains(box.getGoodCategory())) {
					rejectOrderLine(line,"Invalid Good Type ('"+box.getGoodCategory()+"') for selected Box Type ('"+box.getType()+"'). Allowed values are : " + allowedGoodCategories);
					continue;
				}
			}
		}
	}
	
	// TODO -------------- REFACTOR#1 END -----------------
	
	@SuppressWarnings("unchecked")
	private void checkConsistentConsumerDetailsByEmail() {
		MultiValueMap linesByEmail = new MultiValueMap();
		for (BatchOrderLine line : orderLines) {
			linesByEmail.put(line.getEmail(), line);
		}
		for (Object email : linesByEmail.keySet()) {
			Collection<BatchOrderLine> linesWithTheSameEmail = linesByEmail.getCollection(email);
			BatchOrderLine firstLine = linesWithTheSameEmail.iterator().next();
			boolean failed = false;
			for (BatchOrderLine line : linesWithTheSameEmail) {
				if (!Arrays.equals(firstLine.getConsumerFields(), line.getConsumerFields())) {
					failed = true;
					break;
				}
			}
			if (failed) {
				for (BatchOrderLine line : linesWithTheSameEmail) {
					rejectOrderLine(line, "The consumer details associated with this email isn't consistent across the batch upload file");
				}
			}
		}
	}

	private void consolidateOrders() {
		while (!orderLines.isEmpty()) {
			BatchOrderLine firstLine = orderLines.remove(0);
			BatchOrder order = new BatchOrder(firstLine);
			if (firstLine.getOrderReference() != null) {
				addToTheOrderAllTheLinesWithTheSameReferenceNumber(firstLine.getOrderReference(), order);
			}
			orders.add(order);
		}
	}

	private void addToTheOrderAllTheLinesWithTheSameReferenceNumber(String orderReference, BatchOrder order) {
		for (int i = 0; i < orderLines.size(); i++) {
			BatchOrderLine line = orderLines.get(i);
			if (orderReference.equals(line.getOrderReference())) {
				order.addLine(line);
				orderLines.remove(i);
			}
		}
	}
	
	private void createShipmentsForBatchOrders() {
		for (Iterator<BatchOrder> it = orders.iterator(); it.hasNext();) {
			BatchOrder order = it.next();
			try {
				shipments.add(createShipment(order));
			} catch (InvalidOrder e) {
				it.remove();
				rejectOrder(order, e.getMessage());
			}
		}
	}
	
	public List<Shipment> getCreatedShipments() {
		return shipments;
	}

	public List<BatchOrderLine> getOrderLines() {
		return initialOrderlines;
	}
	private void rejectOrder(BatchOrder order, String message) {
		log.warn("Error occured for order with orderReference " + order.getOrderReference() + ": " + message);
		for (BatchOrderLine orderLine : order.getLines()) {
			orderLine.addError("The entire order failed due to: " + message);
		}
	}

	private void rejectOrderLine(BatchOrderLine orderLine, String message) {
		log.warn("Error occured for order line no. " + orderLine.getRowNum() + ": " + message);
		orderLine.addError(message);
	}
	
	public void setPlatformService(PlatformService platformService) {
		this.platformService = platformService;
	}
	
	public void setConsumerService(ConsumerService consumerService) {
		this.consumerService = consumerService;
	}

	public void setMerchantExistingOrderReferences(Set<String> merchantExistingOrderReferences) {
		this.merchantExistingOrderReferences = merchantExistingOrderReferences;
	}

	public List<BatchOrder> getTestOrders() {
		return orders;
	}
	
	@SuppressWarnings("serial")
	public static class InvalidOrder extends RuntimeException {
		public InvalidOrder(String message) {
			super(message);
		}
	}
	
	
	// TODO ---------------------- REFACTOR#2 START ------------------------
	
	// TODO extract method object from this
	// 1) convert local variables to fields of the new class
	// 2) pass the method parameters through the constructor of the new class
	// 3) have an (initial) execute() method instead of processOrder(order)
	// TODO Extra! then try to have it as a static inner class --> and then pop it out as a top-level separate class
	
	public Shipment createShipment(BatchOrder order) {
		Merchant merchant = uploadRequest.getMerchant();
		Date appointmentDate = DateUtils.truncateTimeStamp(uploadRequest.getAppointmentDate());
		validateOrder(order);
		
		Consumer consumer = consumerService.getConsumerByEmail(order.getEmail());
		if (consumer != null && !consumer.isActivated()) {
			throw new InvalidOrder("The consumer is inactive");
		}
		
		ServicePlatform consumerPlatform =  platformService.getPlatformForPostalCode(order.getPostalCode());
		if (consumerPlatform == null) {
			throw new InvalidOrder("The consumer postal code doesn't match any Platform");
		}
		
		Appointment appointment = null;
		if (consumer == null) {
			consumer = createNewConsumer(order);
		} else {
			updateExistingConsumer(order, consumer);
			appointment = consumerService.getConsumerAppointmentAtDate(consumer.getId(), appointmentDate);
		}

		if (appointment == null) {
			// create a new appointment
			TimeRange serviceSlot = consumerPlatform.getServiceSlots().first();
			
			appointment = consumerService.createAppointmentOnServiceSlot(consumer, appointmentDate, serviceSlot);
			if (appointment == null) {
				throw new RuntimeException("Could not create appointment !");
			}
		}

		// get the Shop in the consumer platform
		Shop shop = merchant.getShopForPlatform(consumerPlatform);
		if (shop == null) {
			throw new InvalidOrder("There are no shops available for the consumer platform");
		}
		
		// create the shipment
		Shipment shipment = new Shipment(shop, consumer);
		shipment.setAppointment(appointment);
		shipment.setReferenceNumber(order.getOrderReference());
		shipment.setDescription(order.getDescription());
		BigDecimal price = CurrencyFormatter.parse(order.getPrice());
		if (price != null && BigDecimal.ZERO.compareTo(price) != 0) {
			shipment.setPaymentStatus(PaymentStatus.CREDITCARD_OPEN);
			shipment.setPrice(price);
		} else {
			shipment.setPaymentStatus(PaymentStatus.NOT_APPLICABLE);
		}
		
		// if there are boxes, create them
		if (order.getLines().size() > 1 || 
		   (order.getLines().get(0).getGoodsType() != null && 
		 	order.getLines().get(0).getBoxType() != null)) {
			for (BatchOrderLine line : order.getLines()) {
				Box box = new Box();
				line.setBox(box);
				box.setType(BoxType.valueOf(line.getBoxType()));
				box.setGoodCategory(GoodCategory.valueOf(line.getGoodsType()));
				shipment.addBox(box);
			}
		}

		order.setShipment(shipment); 			
		return shipment;
	}
	

	private void validateOrder(BatchOrder order) {
		validateAllLinesAreValid(order);
		validateLinesHaveIdenticalConsumerInformation(order);
		validateLinesOfConsolidatedOrderHaveBoxTypeAndGoodsType(order);
		validateLinesHaveTheSamePrice(order);
	}

	private void validateAllLinesAreValid(BatchOrder order) {
		for (BatchOrderLine line : order.getLines()) {
			if (line.hasErrors()) {
				throw new InvalidOrder("Line(s) of this order are invalid");
			}
		}
	}

	private void validateLinesHaveIdenticalConsumerInformation(BatchOrder order) {
		BatchOrderLine firstLine = order.getLines().iterator().next();
		for (BatchOrderLine line : order.getLines()) {
			if (!Arrays.equals(firstLine.getConsumerFields(), line.getConsumerFields())) {
				throw new InvalidOrder("Lines of this order have different consumer information");
			}
		}
	}

	private void validateLinesOfConsolidatedOrderHaveBoxTypeAndGoodsType(BatchOrder order) {
		if (order.getLines().size() > 1) {
			for (BatchOrderLine line : order.getLines()) {
				if (line.getBoxType() == null || line.getGoodsType() == null) {
					throw new InvalidOrder("Line(s) of this order have empty BoxType or GoodsType");
				}
			}
		}
	}
	
	private void validateLinesHaveTheSamePrice(BatchOrder order) {
		BatchOrderLine firstLine = order.getLines().iterator().next();
		for (BatchOrderLine line : order.getLines()) {
			if (!EqualsHelper.equals(firstLine.getPrice(), line.getPrice())) {
				throw new InvalidOrder("Lines of this order have different prices");
			}
		}
	}
	
	private Consumer createNewConsumer(BatchOrder order) {
		Consumer consumer = new Consumer();
		consumer.setFirstName(order.getFirstName());
		consumer.setLastName(order.getLastName());
		consumer.setPhoneNumber(order.getPhoneNumber());
		consumer.setEmailAddress(order.getEmail());
		consumer.getAddress().setStreet(order.getStreet());
		consumer.getAddress().setNumber(order.getStreetNr());
		consumer.getAddress().setCity(order.getTown());
		consumer.getAddress().setPostalCode(order.getPostalCode());
		consumer.getAddress().setBox(order.getFloor());
		consumerService.createConsumer(consumer);
		return consumer;
	}

	private void updateExistingConsumer(BatchOrder order, Consumer consumer) {
		consumer.setFirstName(order.getFirstName());
		consumer.setLastName(order.getLastName());
		consumer.setPhoneNumber(order.getPhoneNumber());
		consumer.getAddress().setStreet(order.getStreet());
		consumer.getAddress().setNumber(order.getStreetNr());
		consumer.getAddress().setCity(order.getTown());
		consumer.getAddress().setPostalCode(order.getPostalCode());
		consumer.getAddress().setBox(order.getFloor());
	}
	
	// TODO ---------------------- REFACTOR#2 END ------------------------
}
