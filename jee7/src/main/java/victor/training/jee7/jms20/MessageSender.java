package victor.training.jee7.jms20;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

@Stateless
public class MessageSender {

	@Inject
	private JMSContext context;

	@Resource(mappedName = "java:global/jms/myRequestQueue")
	private Queue queue;

	public void sendMessage(String message) {
		MyJavaMessage objectToSend = new MyJavaMessage(message);
		context.createProducer()
			.setDeliveryDelay(1000) // !!
			.send(queue, objectToSend);
	}
}