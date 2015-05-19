package victor.training.eepatterns.httpevents;

import javax.servlet.AsyncContext;

public class ClientConnection {

	private static int NEXT_ID = 1;
	private final int id = NEXT_ID++;
	
	private final AsyncContext asyncContext;

	public ClientConnection(AsyncContext asyncContext) {
		this.asyncContext = asyncContext;
	}

	public void sendAndCommit(String message) {
		
		send(message);
		commit();
	}

	public void send(String message) {
		try {
			asyncContext.getResponse().getWriter().println(message);
			System.out.println("Wrote event for browser connection "+id);
		} catch (Exception e) {
			System.err.println("Cannot write message: " + e);
		}
	}

	public void commit() {
		try {
			asyncContext.complete();
			System.out.println("Closed HTTP Connection for browser connection "+id);
		} catch (Exception e) {
			System.err.println("Cannot finalize async request: " + e);
		}
	}
}
