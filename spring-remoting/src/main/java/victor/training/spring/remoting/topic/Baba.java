package victor.training.spring.remoting.topic;

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

public class Baba implements SessionAwareMessageListener<TextMessage> {
	static private int counter = 1;
	private static Logger log = LoggerFactory.getLogger(Baba.class + "#" + (counter ++));
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	@Qualifier("barfa")
	private Destination barfa;

	@Override
	public void onMessage(TextMessage message, Session session) throws JMSException {
		log.debug("I just found out that: " + message.getText());		
	}
	
	public void broadcastGossip(String gossip) {
		log.debug("Broadcasting gossip: " + gossip);
		jmsTemplate.send(barfa, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {				
				return session.createTextMessage("gossip");
			}
		});
		log.debug("Broadcast done");
	}

}
