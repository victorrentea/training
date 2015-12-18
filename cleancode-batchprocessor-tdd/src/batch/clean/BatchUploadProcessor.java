package batch.clean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.map.MultiValueMap;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import batch.clean.BatchOrderProcessor.InvalidOrder;
import batch.model.domain.Box;
import batch.model.domain.BoxType;
import batch.model.domain.GoodCategory;
import batch.model.domain.Shipment;
import batch.model.upload.BatchOrder;
import batch.model.upload.BatchOrderLine;
import batch.model.upload.BatchUpload;
import batch.services.ConsumerService;
import batch.services.PlatformService;

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
		checkConsumerFieldsConsistency();
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
			try {
				validateFieldConstraints(line);
				validateUniqueOrderReference(line);
				Box box = createBoxForOrderLine(line);
				line.setBox(box);
				validateBoxTypeIsAllowed(box);
				validateBoxTypeVersusGoodCategory(box);
			} catch (InvalidOrderLine e) {
				rejectOrderLine(line, e.getMessage());
			}
		}
	}
	
	private void validateFieldConstraints(BatchOrderLine orderLine) {
		InvalidValue[] validationMessages = lineValidator.getInvalidValues(orderLine);
		if (validationMessages.length > 0) {
			throw new InvalidOrderLine(Arrays.toString(validationMessages));
		}
	}

	private void validateUniqueOrderReference(BatchOrderLine orderLine) {
		if (orderLine.getOrderReference() != null && 
			merchantExistingOrderReferences.contains(orderLine.getOrderReference())) {
			throw new InvalidOrderLine("This order reference was previously used for this merchant");
		}
	}
	
	private Box createBoxForOrderLine(BatchOrderLine line) {
		Box box = new Box();
		if (line.getGoodsType() != null) {
			box.setGoodCategory(GoodCategory.valueOf(line.getGoodsType()));
		}
		if (line.getBoxType() != null)  {
			box.setType(BoxType.valueOf(line.getBoxType()));
		}
		return box;
	}

	private void validateBoxTypeVersusGoodCategory(Box box) {
		if (box.getType() != null ^ box.getGoodCategory() != null) {
			throw new InvalidOrderLine("The box type and good category are either BOTH specified, either NONE");
		}
		if (box.getType()!=null && box.getGoodCategory()!=null) {
			Set<GoodCategory> allowedGoodCategories = GoodCategory.getGoodCategories(box.getType());
			if (!allowedGoodCategories.contains(box.getGoodCategory())) {
				throw new InvalidOrderLine("Invalid Good Type ('"+box.getGoodCategory()+"') for selected Box Type ('"+box.getType()+"'). Allowed values are : " + allowedGoodCategories);
			}
		}
	}
	
	private void validateBoxTypeIsAllowed(Box box) {
		Set<BoxType> allowedBoxTypes = uploadRequest.getMerchant().getAllowedBoxTypes();
		if (box.getType() != null && !allowedBoxTypes.contains(box.getType())) {
			throw new InvalidOrderLine("The Box type ('"+box.getType()+"') is not allowed for this merchant");
		}
	}
	
	@SuppressWarnings("serial")
	private static class InvalidOrderLine extends RuntimeException {
		public InvalidOrderLine(String message) {
			super(message);
		}
	}
	
	private void rejectOrderLine(BatchOrderLine orderLine, String message) {
		log.warn("Error occured for order line no. " + orderLine.getRowNum() + ": " + message);
		orderLine.addError(message);
	}
	
	// TODO -------------- REFACTOR#1 END -----------------

	@SuppressWarnings("unchecked")
	private void checkConsumerFieldsConsistency() {
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
				BatchOrderProcessor orderProcessor = new BatchOrderProcessor(uploadRequest, order);
				orderProcessor.setConsumerService(consumerService);
				orderProcessor.setPlatformService(platformService);
				shipments.add(orderProcessor.execute());
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

}
