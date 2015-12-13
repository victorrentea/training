package victor.training.spring.jms;

import java.util.UUID;

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
import org.springframework.stereotype.Service;

@Service
public class QuestionSender {
	private final static Logger log = LoggerFactory.getLogger(QuestionSender.class);
	
	@Autowired
	@Qualifier("questions")
	private Destination questions;
	
	@Autowired
	@Qualifier("briefAnswers")
	private Destination briefAnswers;
	
	@Autowired
	@Qualifier("extendedAnswers")
	private Destination extendedAnswers;
	
	@Autowired
	private JmsTemplate jmsTemplate;

	
	public String sendQuestion() throws JMSException {
		log.debug("Sending question...");
		final String correlationID = UUID.randomUUID().toString();
		jmsTemplate.send(questions, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage();
				textMessage.setText("Question1");
				textMessage.setJMSCorrelationID(correlationID);
				return textMessage;
			}
		});
		log.debug("Sent question. Awaiting brief response...");
		TextMessage briefAnswer = (TextMessage) jmsTemplate.receiveSelected(briefAnswers, "JMSCorrelationID='"+correlationID+"'");
		log.debug("Got brief answer: " + briefAnswer.getText());
		return briefAnswer.getText();
		
	}
}

