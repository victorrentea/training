package victor.training.eepatterns.payloadextractor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

public class PayloadExtractor {

	@Inject
	private DeadLetterSender deadLetterSender;

	public static final String CONSUME_METHOD_NAME = "consume";

	@AroundInvoke
	public Object onMessage(InvocationContext invocation) throws Exception {
		Object mdb = invocation.getTarget();

		if (invocation.getMethod().getName().equals("onMessage")) {
			Message message = (Message) invocation.getParameters()[0];
			try {
				Object payload = extractPayload(message);
				invokeConsumeMethod(mdb, payload);
			} catch (Exception e) {
				System.out.println("Report error: sending message to dead letter");
				e.printStackTrace();
				deadLetterSender.sendDeadLetter(message, e.getMessage(), e);
			}
		}
		return invocation.proceed();
	}

	@SuppressWarnings("unchecked")
	private Object extractPayload(Message message) {
		try {
			if (message instanceof TextMessage) {
				return ((TextMessage) message).getText();
			} else if (message instanceof ObjectMessage) {
				return ((ObjectMessage) message).getObject();
			} else if (message instanceof MapMessage) {
				Map<String, Object> map = new HashMap<String, Object>();
				MapMessage mapMessage = (MapMessage) message;
				for (Enumeration<String> e = mapMessage.getMapNames(); e.hasMoreElements();) {
					String key = e.nextElement();
					map.put(key, mapMessage.getObject(key));
				}
				return map;
			} else {
				throw new IllegalArgumentException("Unsupported message type: " + message.getClass());
			}
		} catch (JMSException e) {
			throw new IllegalArgumentException("Could not extract payload from " + message.getClass().getSimpleName());
		}
	}

	private void invokeConsumeMethod(Object mdb, Object payload) {
		try {

			mdb.getClass().getMethod(CONSUME_METHOD_NAME, payload.getClass()).invoke(mdb, payload);
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("No '" + CONSUME_METHOD_NAME + "' method in MDB: " + mdb.getClass()
					+ " to receive parameters of type " + payload.getClass());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
