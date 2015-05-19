package victor.training.jee6.servlet3;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/async", asyncSupported = true)
public class AsyncServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		final AsyncContext asyncContext = req.startAsync();

		// DON'T TRY THIS AT HOME: Working with threads in a Container !!
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					asyncContext.getResponse().getWriter().println(new Date() + ": Asynchronous Response");
					System.out.println("Release pending HTTP connection");
					asyncContext.complete();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, 10 * 1000);
		resp.getWriter().println(new Date() + ": Async Servlet returns leaving the connection open");
	}
}
