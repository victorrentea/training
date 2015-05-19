package victor.training.jee7.jms20;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;

@MessageDriven(mappedName = "java:global/jms/myRequestQueue", 
activationConfig = @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:global/jms/myRequestQueue"))
public class MDB implements MessageListener {

	@Inject
	JMSContext context;

	@Resource(mappedName = "java:global/jms/myResponseQueue")
	Queue responseQueue;

	@Override
	public void onMessage(Message message) {
		try {
			// In JMS 1.1:
			// ObjectMessage objectMessage = (ObjectMessage)message;
			// BusinessObject payload = (BusinessObject)objectMessage.getObject();
			MyJavaMessage payload = message.getBody(MyJavaMessage.class);
			context.createProducer().send(responseQueue, new MyJavaMessage("MDB:Processed " + payload));
		} catch (JMSException e) {
			System.err.println("Error while fetching message payload: " + e.getMessage());
		}
	}
}