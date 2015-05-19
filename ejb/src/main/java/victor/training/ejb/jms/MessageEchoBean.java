package victor.training.ejb.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/test_in"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class MessageEchoBean implements MessageListener {

	@EJB
	private MyMessageSender sender;

	@Override
	public void onMessage(Message message) {
		System.out.println("MDB received message");
		TextMessage textMessage = (TextMessage) message;

		try {
			sender.sendMessage(textMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
		System.out.println("MDB completed");
	}
}