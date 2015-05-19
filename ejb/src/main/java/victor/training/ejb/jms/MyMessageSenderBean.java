package victor.training.ejb.jms;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

@Stateless
public class MyMessageSenderBean implements MyMessageSender {
	
	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory factory;
	
	@Resource(mappedName = "java:/queue/test_out")
	private Queue queue;

	@Override
	public void sendMessage(String payload) {
		Connection connection = null;
		try {
			connection = factory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			TextMessage textMessage = session.createTextMessage();
			textMessage.setText(payload);
			session.createProducer(queue).send(textMessage);
		} catch (Exception e) {
			throw new RuntimeException("Could not send message", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
				}
			}
		}

	}

}
