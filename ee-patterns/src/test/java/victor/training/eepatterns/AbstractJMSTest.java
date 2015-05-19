package victor.training.eepatterns;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import org.junit.After;
import org.junit.Before;

public class AbstractJMSTest extends AbstractArquillianTest {

	@Resource(mappedName = "/ConnectionFactory")
	private ConnectionFactory factory;
	protected Connection jmsConnection;
	protected Session jmsSession;

	@Before
	public void openJMSConnection() throws JMSException {
		jmsConnection= factory.createConnection();
		jmsSession = jmsConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		jmsConnection.start();
	}

	@After
	public void closeJMS() throws JMSException {
		jmsConnection.close();
	}

}
