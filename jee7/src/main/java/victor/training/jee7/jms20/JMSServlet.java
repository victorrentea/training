package victor.training.jee7.jms20;

import java.io.IOException;

import javax.ejb.EJB;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@JMSDestinationDefinitions({
	@JMSDestinationDefinition(name = "java:global/jms/myRequestQueue", interfaceName = "javax.jms.Queue", destinationName = "queue8", description = "My Queue11"),
	@JMSDestinationDefinition(name = "java:global/jms/myResponseQueue", interfaceName = "javax.jms.Queue", destinationName = "queue9", description = "My Queue2211")
})
@WebServlet(urlPatterns = { "/jms" })
public class JMSServlet extends HttpServlet {

	@EJB
	private MessageSender sender;

	@EJB
	private MessageReceiverSync receiver;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String m = "Hello there";
		sender.sendMessage("victor.training.jee7.jaxb");
		ServletOutputStream responseStream = response.getOutputStream();
		responseStream.println(String.format("Message sent: %1$s.<br>", m));
		responseStream.println("Blocking for message...<br>");
		String message = receiver.receiveMessage();
		responseStream.println("Message received: " + message);
		responseStream.close();
	}
}
	