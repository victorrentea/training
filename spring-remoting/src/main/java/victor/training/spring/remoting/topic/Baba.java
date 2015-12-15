package victor.training.spring.remoting.topic;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.listener.SessionAwareMessageListener;

public class Baba implements SessionAwareMessageListener<TextMessage>, BeanNameAware {
	static private int counter = 1;
	private static Logger log = LoggerFactory.getLogger(Baba.class.getCanonicalName() + "#" + (counter ++));
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	@Qualifier("barfa")
	private Destination barfa;
	
	private String name;

	@Override
	public void onMessage(TextMessage message, Session session) throws JMSException {
		log.debug("({}) I just found out that: {}", name, message.getText());		
	}
	
	public void broadcastGossip(String gossip) {
		log.debug("({}) Broadcasting gossip: {}", name, gossip);
		jmsTemplate.send(barfa, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {				
				return session.createTextMessage("gossip");
			}
		});
		log.debug("({}) Broadcast done", name);
	}

	@Override
	public void setBeanName(String name) {
		this.name = name;			
	}
	
	@Override
	public String toString() {
		return name;
	}
}
