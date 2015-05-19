package victor.training.eepatterns.payloadextractor.mdb;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import victor.training.eepatterns.payloadextractor.DeadLetterSender;
import victor.training.eepatterns.payloadextractor.facade.AsyncBusinessFacade;

//@MessageDriven(activationConfig = {
//		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/map_in"),
//		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class MapMessageMDB implements MessageListener {

	@Inject
	private AsyncBusinessFacade facade;

	@Inject
	private DeadLetterSender deadLetterSender;
	

	@Override
	public void onMessage(Message message) {
		System.out.println("MDB received message");
		if (message instanceof MapMessage) {
			try {
				MapMessage mapMessage = (MapMessage) message;
				facade.consumeMapMessage(mapMessage.getString("param1"), mapMessage.getString("param2"));
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
