package victor.training.jee7.jms20;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

@Stateless
public class MessageReceiverSync {

	@Inject
	private JMSContext context;

	@Resource(mappedName = "java:global/jms/myResponseQueue")
	Queue responseQueue;

	public String receiveMessage() {
		MyJavaMessage message = context
				.createConsumer(responseQueue)
				.receiveBody(MyJavaMessage.class);
		return "MessageReceiverSync:Received " + message;
	}
}