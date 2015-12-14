package victor.training.spring.remoting.queue;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.listener.SessionAwareMessageListener;

public class DummyResponder implements SessionAwareMessageListener<TextMessage> {
	private final static Logger log = LoggerFactory.getLogger(DummyResponder.class);

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	@Qualifier("briefAnswers")
	private Destination briefAnswers;

	@Override
	public void onMessage(final TextMessage message, Session session) throws JMSException {
		final String replyText = message.getText() + " Pong!";
		log.debug("Got question. Answering back with: {}", replyText);
		
		jmsTemplate.send(briefAnswers, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage replyTextMessage = session.createTextMessage(replyText);
				replyTextMessage.setJMSCorrelationID(message.getJMSCorrelationID());
				return replyTextMessage;
			}
		});
	}
}
