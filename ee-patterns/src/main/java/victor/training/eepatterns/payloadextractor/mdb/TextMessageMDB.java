package victor.training.eepatterns.payloadextractor.mdb;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import victor.training.eepatterns.payloadextractor.DeadLetterSender;
import victor.training.eepatterns.payloadextractor.facade.AsyncBusinessFacade;

//@MessageDriven(activationConfig = {
//		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/text_in"),
//		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class TextMessageMDB implements MessageListener {

	@Inject
	private AsyncBusinessFacade facade;

	@Inject
	private DeadLetterSender deadLetterSender;

	@Override
	public void onMessage(Message message) {
		System.out.println("MDB received message");
		if (message instanceof TextMessage) {
			try {
				facade.consumeTextMessage(((TextMessage) message).getText());
			} catch (JMSException e) {
				deadLetterSender.sendDeadLetter(message, "Error reading message", e);
			} catch (RuntimeException e) {
				deadLetterSender.sendDeadLetter(message, "Error processing message", e);
			}
		} else {
			deadLetterSender.sendDeadLetter(message, "Invalid message type", null);
		}
	}

}
