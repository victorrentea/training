package victor.training.ejb.jms;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import victor.training.ejb.EJBTest;

@RunWith(Arquillian.class)
public class TestJMS extends EJBTest {

	@Resource(mappedName = "/queue/test_in")
	private Queue requestQueue;
	
	@Resource(mappedName = "/queue/test_out")
	private Queue responseQueue;

	@Resource(mappedName = "/ConnectionFactory")
	private ConnectionFactory factory;

	@Test
	public void shouldBeAbleToSendMessage() throws Exception {

		String messageBody = "ping";

		Connection connection = factory.createConnection();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		connection.start();

		Message request = session.createTextMessage(messageBody);

		System.out.println("TEST: Sending request JMS message");
		session.createProducer(requestQueue).send(request);
		
		// do other stuff
		System.out.println("TEST: Waiting for response JMS message");
		Message response = session.createConsumer(responseQueue).receive();

		System.out.println("TEST: Received response");
		Assert.assertEquals("Should have responded with same message", messageBody, ((TextMessage) response).getText());

	}

}
