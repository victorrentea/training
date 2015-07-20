package victor.training.eepatterns.contextholder.thread;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import victor.training.eepatterns.contextholder.ContextKey;

/**
 * not real
 * @author VictorRentea
 *
 */
@WebServlet(urlPatterns="/ThreadLocal")
public class MyServlet extends HttpServlet {

	@Inject
	private MyFacade facade;
	
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		ThreadLocalContextHolder.put(ContextKey.USERNAME_KEY, "Gica");
		facade.businessMethod();
	}
}
