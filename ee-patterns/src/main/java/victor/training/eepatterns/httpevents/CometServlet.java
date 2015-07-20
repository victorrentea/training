package victor.training.eepatterns.httpevents;

import java.io.IOException;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(name="Publisher", urlPatterns = "/publisher", asyncSupported = true)
public class CometServlet extends HttpServlet {
	
	@Inject
	private Event<ClientConnection> newClientEvent;
	
	@Inject
	private Business business;

	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("received request");
		business.activate();
		
		AsyncContext asyncContext = request.startAsync();
		newClientEvent.fire(new ClientConnection(asyncContext));
		
		// TODO talk: Event delivery: Real-Time vs. Batch
		// TODO talk: pub/sub: the client specifies in its request the topics it�s interested in

	}

}
