package victor.training.eepatterns.httpevents;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;

/**
 * Snippet inspired from Adam Bien's book: Real World Java EE Patterns - Rethinking Best Practices, 2nd ed., 2012 
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class CometGateway {

	private final ConcurrentLinkedQueue<ClientConnection> clients = new ConcurrentLinkedQueue<ClientConnection>();

	public void onNewClient(@Observes ClientConnection event) {
		clients.add(event);
	}

	public void onBusinessEvent(@Observes BusinessEvent event) {
		System.out.println("Dispatching event '" + event.getText() +"'");
		for (ClientConnection client : clients) {
			client.sendAndCommit(event.getText());
			clients.remove(client);
		}
	}

}
