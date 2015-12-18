package batch;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.easymock.Capture;
import org.easymock.IAnswer;
import org.easymock.IExpectationSetters;
import org.junit.Before;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;

import batch.dirty.BatchUploadProcessor;
import batch.model.domain.Address;
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
import batch.model.domain.Status;
import batch.model.domain.TimeRange;
import batch.model.upload.BatchOrderLine;
import batch.model.upload.BatchUpload;
import batch.model.upload.BatchOrderLine.Builder;
import batch.services.ConsumerService;
import batch.services.PlatformService;
import batch.util.DateUtils;



@SuppressWarnings({"unchecked", "serial"})
public class BatchUploadProcessorTest extends UnitilsJUnit4 {
	
	private static final Date DATE_01_JAN = DateUtils.parseDate("01/01/2015");
	private static final Date DATE_02_JAN = DateUtils.parseDate("02/01/2015");
	private static final TimeRange SERVICE_SLOT_18_TO_20 = new TimeRange("18:00", "20:00");

	
	private BatchUploadProcessor processor;
	
	// input for the processor
	private ConsumerService consumerService = createMock(ConsumerService.class);
	private PlatformService platformService = createMock(PlatformService.class);
	

	//output from the processor - for convenience
	private List<BatchOrderLine> orderLines;
	private Shipment shipment;
	private Box box;
	
	// test data
	private int rowNum = 0;
	private Shop shop = new Shop();
	private ServicePlatform consumerPlatform = new ServicePlatform();

	private Date appointmentDate = DATE_01_JAN;
	private Set<String> existingOrderReferences = new HashSet<String>();
	private Set<BoxType> allowedBoxTypesPerMerchant = new HashSet<BoxType>(Arrays.asList(BoxType.values()));
	
	
	@Before
	public void initializeMocks() {
		consumerPlatform.getServiceSlots().add(new TimeRange("18:00", "20:00"));
		shop.setPlatform(consumerPlatform);
		resetConsumerService();
		resetPlatformService();
	}
	
	@Test
	public void testOrderLinesValidation() {
		System.out.println(runValidation(newLine()).getErrors());
		assertFalse(runValidation(newLine()).hasErrors());
		assertTrue(runValidation(newLine().withFirstName(null)).hasErrors());
		assertTrue(runValidation(newLine().withLastName(null)).hasErrors());
		assertTrue(runValidation(newLine().withPhoneNumber(null)).hasErrors());
		assertTrue(runValidation(newLine().withStreet(null)).hasErrors());
		assertTrue(runValidation(newLine().withStreetNr(null)).hasErrors());
		assertFalse(runValidation(newLine().withFloor(null)).hasErrors());
		assertTrue(runValidation(newLine().withFloor("4A")).hasErrors());
		assertTrue(runValidation(newLine().withPostalCode(null)).hasErrors());
		assertTrue(runValidation(newLine().withPostalCode("1234a")).hasErrors());
		assertTrue(runValidation(newLine().withPostalCode("123a")).hasErrors());
		assertTrue(runValidation(newLine().withTown(null)).hasErrors());
		assertTrue(runValidation(newLine().withEmail(null)).hasErrors());
		assertTrue(runValidation(newLine().withEmail("not an email")).hasErrors());
		assertFalse(runValidation(newLine().withPrice(null)).hasErrors());
		assertTrue(runValidation(newLine().withPrice("not a price")).hasErrors());
		assertTrue(runValidation(newLine().withBoxType("not a boxtyp")).hasErrors());
		assertTrue(runValidation(newLine().withGoodsType("not a goods type")).hasErrors());
		assertFalse(runValidation(newLine().withGoodsType(null).withBoxType(null)).hasErrors());
		assertTrue(runValidation(newLine().withBoxType("SMALL").withGoodsType("FRESH_COOL")).hasErrors());
	}
	
	@Test
	public void basicOrderConsolidation() {
		run(newLine(), newLine());
		assertEquals(1, processor.getTestOrders().size());
		assertEquals(2, processor.getTestOrders().get(0).getLines().size());
	}

	@Test
	public void orderConsolidationForNullOrderReference() {
		run(newLine(), newLine(), 
			newLine().withOrderReference(null), newLine().withOrderReference(null));
		assertEquals(3, processor.getTestOrders().size());
	}
	
	@Test
	public void orderReferenceThatAlreadyExistsForMerchantFailsTheOrderLine() {
		existingOrderReferences.add("ref1234");
		run(newLine());
		assertAllFailed();
	}
	
	@Test
	public void oneFailedOrderLineResultsInTheWholeOrderBeingRejected() {
		run(newLine(), newLine().withBoxType("invalid box type"));
		assertAllFailed();
	}

	@Test
	public void differentConsumerInformationWithinTheOrder_failsTheEntireOrder() {
		run(newLine(), newLine().withFirstName("Another FName"));
		assertAllFailed();
		run(newLine(), newLine().withLastName("Another LName"));
		assertAllFailed();
		run(newLine(), newLine().withPhoneNumber("Another Phone"));
		assertAllFailed();
		run(newLine(), newLine().withStreet("Another Street"));
		assertAllFailed();
		run(newLine(), newLine().withStreetNr("Another Nr"));
		assertAllFailed();
		run(newLine(), newLine().withFloor("99"));
		assertAllFailed();
		run(newLine(), newLine().withPostalCode("0000"));
		assertAllFailed();
		run(newLine(), newLine().withTown("Another Town"));
		assertAllFailed();
		run(newLine(), newLine().withEmail("another@email.com"));
		assertAllFailed();
	}
	
	@Test
	public void twoOrderLinesWithSameEmailMustHaveTheSameConsumerDetails() {
		run(newLine().withOrderReference(null),
			newLine().withOrderReference(null).withFirstName("OtherFName"));
		assertAllFailed();
	}
	
	@Test
	public void orderLinesOfConsolidatedOrderMustHaveBoxTypeAndGoodsType() {
		run(newLine(), newLine().withBoxType(null));
		assertAllFailed();
		
		run(newLine(), newLine().withGoodsType(null));
		assertAllFailed();
	}
	
	@Test
	public void orderLineWithBoxTypeNotAllowedForTheMerchantFails() {
		allowedBoxTypesPerMerchant = Collections.singleton(BoxType.SMALL);
		run(newLine().withBoxType("LARGE_COOLED").withGoodsType("FROZEN"));
		assertAllFailed();
	}
	
	@Test
	public void orderWithOneOrderLineThatHasJustBoxTypeAndNoGoodsTypeFails() {
		run(newLine().withBoxType(null));
		assertAllFailed();
	}
	
	@Test
	public void orderWithOneOrderLineThatHasJustGoodsTypeAndNoBoxTypeFails() {
		run(newLine().withGoodsType(null));
		assertAllFailed();
	}
	
	
	@Test
	public void soleOrderLineOfOrderCanHaveEmptyBoxTypeAndGoodsType() {
		run(newLine().withBoxType(null).withGoodsType(null));
		assertEquals(1, processor.getTestOrders().size());
	}
	
	@Test
	public void linesOfTheSameOrderMustHaveTheSamePrice() {
		run(newLine(), newLine().withPrice("99"));
		assertAllFailed();
	}
	
	@Test
	public void linesOfTheSameOrderCanAllHaveNullPrice() {
		run(newLine().withPrice(null), newLine().withPrice(null));
		assertEquals(1, processor.getTestOrders().size());
	}
	
	@Test
	public void aBoxIsCreatedForEachValidOrderLine() {
		run(newLine());
		Box boxOnBatchOrderLine = orderLines.get(0).getBox();
		assertNotNull(boxOnBatchOrderLine);
		assertEquals(BoxType.LARGE, boxOnBatchOrderLine.getType());
		assertEquals(GoodCategory.NON_FOOD, boxOnBatchOrderLine.getGoodCategory());
	}
	
	@Test
	public void aNonCooledBoxIsProcessedSuccessful() {
		run(newLine().withBoxType(""+BoxType.LARGE));
		assertNotNull(box);
	}
	
	@Test
	public void activeConsumerFromDBIsAssignedToNewlyCreatedShipmentWhichIsLinkedToTheBatchOrder() {
		run(newLine());
		assertNotNull(processor.getTestOrders().get(0).getShipment());
		assertNotNull(processor.getTestOrders().get(0).getShipment().getReceiver());
	}
	
	@Test
	public void inactiveConsumerFromDBFailsTheEntireOrder() {
		final Consumer inactiveConsumer = new Consumer();
		inactiveConsumer.setStatus(Status.DEACTIVATED);
		reset(consumerService);
		expect(consumerService.getConsumerByEmail((String) anyObject()))
			.andReturn(inactiveConsumer);
		
		run(newLine().withEmail("inactive.consumer@bpost.be"), 
			newLine().withEmail("inactive.consumer@bpost.be"));
		assertAllFailed();
	}
	
	@Test
	public void newConsumerWithIncorrectPostalCodeFailsTheOrder() {
		expectNewConsumer();
		reset(platformService);
		expect(platformService.getPlatformForPostalCode((String) anyObject())).andReturn(null);
		run(newLine());
		assertAllFailed();
	}
	
	@Test
	public void newConsumerIsRegisteredSuccessfully() {
		expectNewConsumer();
		Capture<Consumer> consumerCapture = new Capture<Consumer>();
		consumerService.createConsumer(capture(consumerCapture));
		expectLastCall();
		
		run(newLine());
		
		Consumer newConsumer = consumerCapture.getValue();
		assertEquals("John",newConsumer.getFirstName());
		assertEquals("Doe", newConsumer.getLastName());
		assertEquals("+40720019564", newConsumer.getPhoneNumber());
		assertEquals("victor.rentea@bpost.be", newConsumer.getEmailAddress());
		Address newAddress = newConsumer.getAddress();
		assertEquals("Vasile Milea", newAddress.getStreet());
		assertEquals("4A", newAddress.getNumber());
		assertEquals("1234", newAddress.getPostalCode());
		assertEquals("Brussels", newAddress.getCity());
		assertEquals("5", newAddress.getBox());
	}
	
	@Test
	public void consumerWithIncorrectPostalCodeFailsTheOrder() {
		expectExistingConsumer();
		reset(platformService);
		expect(platformService.getPlatformForPostalCode((String) anyObject())).andReturn(null);
		run(newLine());
		assertAllFailed();
	}
	
	@Test
	public void existingConsumerIsUpdatedHisDetails() {
		Consumer consumer = expectExistingConsumer();
		
		run(newLine()
				.withFirstName("NewFName")
				.withLastName("NewLName")
				.withPhoneNumber("NewPhone"));
		
		assertEquals("NewFName", consumer.getFirstName());
		assertEquals("NewLName", consumer.getLastName());
		assertEquals("NewPhone", consumer.getPhoneNumber());
	}

	
	@Test
	public void existingConsumerIsUpdatedHisAddressWhenTheNewAddressIsFromTheSamePlatform() {
		Consumer consumer = expectExistingConsumer();
		
		reset(platformService);
		expect(platformService.getPlatformForPostalCode(eq("1234"))).andReturn(consumerPlatform);
		
		run(newLine());
		
		Address actualAddress = consumer.getAddress();
		assertEquals("Vasile Milea", actualAddress.getStreet());
		assertEquals("4A", actualAddress.getNumber());
		assertEquals("1234", actualAddress.getPostalCode());
		assertEquals("Brussels", actualAddress.getCity());
		assertEquals("5", actualAddress.getBox());
	}

	
	@Test
	public void existingAppointmentsForThatDayAreRetrievedForExistingConsumers() {
		reset(consumerService);
		expectFindConsumerByEmail();
		Capture<Long> consumerIdCapture= new Capture<>();
		expect(consumerService.getConsumerAppointmentAtDate(capture(consumerIdCapture), eq(appointmentDate))).andReturn(null).times(2);
		expectCreateAppointment().times(2);
		
		run(newLine(), newLineWithDifferentConsumer());
	}
	
	@Test 
	public void existingAppointmentForThatDayIsUsedForExistingConsumer() {
		reset(consumerService);
		expectFindConsumerByEmail();
		final Capture<Long> consumerIdCapture= new Capture<>();
		final Appointment existingAppointment = new Appointment();
		expect(consumerService.getConsumerAppointmentAtDate(capture(consumerIdCapture), eq(appointmentDate)))
			.andReturn(existingAppointment);
		
		run(newLine());
		
		assertEquals(existingAppointment, shipment.getAppointment());
	}
	
	@Test
	public void whenNoAppointmentExistsForExistingConsumers_aNewOneShotAppointmentIsCreatedOnTheFirstAvailableServiceSlot() {
		Capture<Consumer> consumerCapture = new Capture<Consumer>(); 
		Capture<TimeRange> slotCapture = new Capture<TimeRange>();
		Appointment appointment = new Appointment();
		
		reset(consumerService);
		expectFindConsumerByEmail();
		expectGetConsumerAppointments();
		expect(consumerService.createAppointmentOnServiceSlot(capture(consumerCapture), 
				eq(appointmentDate), capture(slotCapture))).andReturn(appointment);
		consumerPlatform.getServiceSlots().add(new TimeRange("16:00", "18:00"));
		
		run(newLine());
		
		TimeRange slot = slotCapture.getValue();
		assertEquals("16:00", slot.getMin());
		assertEquals("18:00", slot.getMax());
	}
	
	@Test
	public void theNewShipmentIsFilledCorrectly() {
		
		run(newLine());
		
		assertEquals(shop, shipment.getSender());
		assertEquals("ref1234", shipment.getReferenceNumber());
		assertEquals("Candies", shipment.getDescription());
		assertEquals(new BigDecimal(new BigInteger("1233"), 2), shipment.getPrice());
		assertEquals(PaymentStatus.CREDITCARD_OPEN, shipment.getPaymentStatus());
		assertEquals(appointmentDate, shipment.getAppointment().getDate());
		
		run(newLine().withPrice(null));
		assertEquals(PaymentStatus.NOT_APPLICABLE, shipment.getPaymentStatus());
		
		run(newLine().withPrice("0"));
		assertEquals(PaymentStatus.NOT_APPLICABLE, shipment.getPaymentStatus());
		
		run(newLine().withGoodsType(null).withBoxType(null));
		assertEquals(0, shipment.getBoxes().size());
	}
	
	@Test
	public void theNewBoxesAreFilledCorrectly() {
		run(newLine().withBoxType("LARGE_COOLED").withGoodsType("FROZEN"));
		
		assertEquals(BoxType.LARGE_COOLED, box.getType());
		assertEquals(shipment, box.getShipment());
		assertEquals(GoodCategory.FROZEN, box.getGoodCategory());
		
	}
	
	/********************************** UTILITIES ***************************************/
	/********************************** UTILITIES ***************************************/
	/********************************** UTILITIES ***************************************/
	
	private void expectNewConsumer() {
		reset(consumerService);
		expect(consumerService.getConsumerByEmail((String) anyObject()))
			.andReturn(null);
		expectGetConsumerAppointments().anyTimes();
		expectCreateAppointment().anyTimes();
	}
	
	private Consumer expectExistingConsumer() {
		reset(consumerService);
		Consumer consumer = new Consumer();
		Address oldAddress = new Address();
		oldAddress.setStreet("Old Street");
		oldAddress.setNumber("old nr");
		oldAddress.setPostalCode("0000");
		oldAddress.setCity("Old City");
		consumer.setAddress(oldAddress);
		expect(consumerService.getConsumerByEmail((String) anyObject()))
			.andReturn(consumer);
		expectGetConsumerAppointments().anyTimes();
		expect(consumerService.createAppointmentOnServiceSlot((Consumer)anyObject(), (Date)anyObject(), (TimeRange)anyObject()))
			.andReturn(new Appointment()).anyTimes();
		return consumer;
	}
	
	
	private Map<String, Consumer> map(final String email, final Consumer consumer) {
		consumer.setEmailAddress(email);
		return new HashMap<String, Consumer>() {{put(email,consumer);}};
	}

	private void assertAllFailed() {
		assertEquals(0, processor.getTestOrders().size());
		for (BatchOrderLine line : processor.getOrderLines()) {
			assertTrue(line.hasErrors());
			assertNull(line.getBox());
		}
	}
	
	private BatchOrderLine runValidation(Builder lineBuilder) {
		return run(lineBuilder).getOrderLines().get(0);
	}
	
	private BatchUploadProcessor run(Builder... lineBuilders) {
		List<BatchOrderLine> lines = new ArrayList<BatchOrderLine>();
		for (Builder builder:lineBuilders) {
			lines.add(builder.build());
		}
		Merchant merchant = new Merchant();
		merchant.setAllowedBoxTypes(allowedBoxTypesPerMerchant);
		merchant.getShops().add(shop);
		processor = new BatchUploadProcessor(new BatchUpload(lines, merchant, appointmentDate));

		replay(consumerService, platformService);
		processor.setConsumerService(consumerService);
		processor.setPlatformService(platformService);
		processor.setMerchantExistingOrderReferences(existingOrderReferences);
		processor.execute();
		orderLines=processor.getOrderLines();
		if (!processor.getCreatedShipments().isEmpty()) {
			shipment = processor.getCreatedShipments().get(0);
			if (!shipment.getBoxes().isEmpty()) {
				box = shipment.getBoxes().iterator().next();
			}
		}
		verify(consumerService, platformService);
		resetConsumerService();
		resetPlatformService();
		return processor;
	}
	
	private void resetConsumerService() {
		reset(consumerService);
		expectFindConsumerByEmail();
		expectGetConsumerAppointments().anyTimes();
		expectCreateAppointment().anyTimes();
	}
	
	private void expectFindConsumerByEmail() {
		final Capture<String> consumerEmailCapture = new Capture<>();
		expect(consumerService.getConsumerByEmail(capture(consumerEmailCapture))).andAnswer(new IAnswer<Consumer>() {
			@Override
			public Consumer answer() throws Throwable {
				String email = consumerEmailCapture.getValue();
				Consumer consumer = new Consumer();
				consumer.setEmailAddress(email);
				Address oldAddress = new Address();
				oldAddress.setStreet("Old Street");
				oldAddress.setNumber("old nr");
				oldAddress.setPostalCode("1234");
				oldAddress.setCity("Old City");
				consumer.setAddress(oldAddress);
				return consumer;
			}
		}).anyTimes();
	}
	
	private IExpectationSetters<Appointment> expectGetConsumerAppointments() {
		return expect(consumerService.getConsumerAppointmentAtDate((Long)anyObject(), (Date)anyObject()))
			.andReturn(null);
	}

	private IExpectationSetters<Appointment> expectCreateAppointment() {
		return expect(consumerService.createAppointmentOnServiceSlot((Consumer)anyObject(), (Date)anyObject(), (TimeRange)anyObject()))
			.andReturn(new Appointment(appointmentDate));
	}
	
	private void resetPlatformService() {
		reset(platformService);
		expect(platformService.getPlatformForPostalCode((String) anyObject())).andReturn(consumerPlatform).anyTimes();
	}
	
	private Builder newLine() {
		return Builder.newLine(rowNum++)
				.withFirstName("John")
				.withLastName("Doe")
				.withPhoneNumber("+40720019564")
				.withStreet("Vasile Milea")
				.withStreetNr("4A")
				.withFloor("5")
				.withPostalCode("1234")
				.withTown("Brussels")
				.withEmail("victor.rentea@bpost.be")
				.withPrice("12.33")
				.withBoxType("LARGE")
				.withGoodsType("NON_FOOD")
				.withOrderReference("ref1234")
				.withOrderDescription("Candies");
	}
	
	private Builder newLineWithDifferentConsumer() {
		return Builder.newLine(rowNum++)
				.withFirstName("Mike")
				.withLastName("Higgins")
				.withPhoneNumber("+3222765522")
				.withStreet("Mikestraat")
				.withStreetNr("2")
				.withFloor("6")
				.withPostalCode("4321")
				.withTown("Brussels")
				.withEmail("mike.higgins@bpost.be")
				.withPrice("12.33")
				.withBoxType("LARGE")
				.withGoodsType("NON_FOOD")
				.withOrderReference("ref4321")
				.withOrderDescription("Candies");
	}
	
	
	private static <T> void assertContains(T value, Collection<T> collection) {
		assertTrue(collection.contains(value));
	}
	
}
