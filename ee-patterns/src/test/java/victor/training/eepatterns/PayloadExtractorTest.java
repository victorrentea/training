package victor.training.eepatterns;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;

import javax.annotation.Resource;
import javax.enterprise.inject.Produces;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import victor.training.eepatterns.payloadextractor.facade.AsyncBusinessFacade;

@RunWith(Arquillian.class)
public class PayloadExtractorTest extends AbstractJMSTest {

	@Mock
	@Produces
	private AsyncBusinessFacade businessFacade;

	@Before
	public void resetMocks() {
		reset(businessFacade);
	}

	@Resource(mappedName = "/queue/text_in")
	private Queue textRequestQueue;

	@Resource(mappedName = "/queue/map_in")
	private Queue mapRequestQueue;

	@Resource(mappedName = "/queue/error_out")
	private Queue errorQueue;

	@Test
	public void textMessageIsDeliveredToConsumeTextMethod() throws Exception {

		TextMessage message = jmsSession.createTextMessage("textMessage");
		jmsSession.createProducer(textRequestQueue).send(message);

		Thread.sleep(200); // message travels ...

		Mockito.verify(businessFacade).consumeTextMessage("textMessage");
	}

	@Test
	public void mapMessageIsDeliveredToConsumeMapMethod() throws Exception {

		jmsSession.createProducer(mapRequestQueue).send(aMapMessage());

		Thread.sleep(200); // message travels ...

		Mockito.verify(businessFacade).consumeMapMessage("value1", "value2");
	}

	@Test
	public void mapMessageToTextQueueIsPutOnErrorQueue() throws Exception {
		MapMessage message = aMapMessage();
		jmsSession.createProducer(textRequestQueue).send(message);

		Message errorMessage = jmsSession.createConsumer(errorQueue).receive(1000);
		assertNotNull("Error message is received", errorMessage);
		assertEquals(message.getJMSCorrelationID(), errorMessage.getJMSCorrelationID());
	}

	@Test
	public void textMessageToMapQueueIsPutOnErrorQueue() throws Exception {
		TextMessage message = jmsSession.createTextMessage("textMessage");
		jmsSession.createProducer(mapRequestQueue).send(message);

		Message errorMessage = jmsSession.createConsumer(errorQueue).receive(1000);
		assertNotNull("Error message is received", errorMessage);
		assertEquals(message.getJMSCorrelationID(), errorMessage.getJMSCorrelationID());
	}

	@Test
	public void failedProcessingOfTextMessageIsPutOnErrorQueue() throws Exception {
		doThrow(RuntimeException.class).when(businessFacade).consumeTextMessage(Mockito.anyString());

		TextMessage message = jmsSession.createTextMessage("textMessage");
		jmsSession.createProducer(textRequestQueue).send(message);

		Message errorMessage = jmsSession.createConsumer(errorQueue).receive(1000);
		assertNotNull("Error message is received", errorMessage);
		assertEquals(message.getJMSCorrelationID(), errorMessage.getJMSCorrelationID());
	}

	@Test
	public void failedProcessingOfMapMessageIsPutOnErrorQueue() throws Exception {
		doThrow(RuntimeException.class).when(businessFacade)
				.consumeMapMessage(Mockito.anyString(), Mockito.anyString());

		MapMessage message = aMapMessage();
		jmsSession.createProducer(mapRequestQueue).send(message);

		Message errorMessage = jmsSession.createConsumer(errorQueue).receive(1000);
		assertNotNull("Error message is received", errorMessage);
		assertEquals(message.getJMSCorrelationID(), errorMessage.getJMSCorrelationID());
	}

	private MapMessage aMapMessage() throws JMSException {
		MapMessage message = jmsSession.createMapMessage();
		message.setString("param1", "value1");
		message.setString("param2", "value2");
		return message;
	}

}
