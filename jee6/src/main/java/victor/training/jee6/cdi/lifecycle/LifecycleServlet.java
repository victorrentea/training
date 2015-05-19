package victor.training.jee6.cdi.lifecycle;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import victor.training.jee6.cdi.lifecycle.beans.ApplicationLocal;
import victor.training.jee6.cdi.lifecycle.beans.DependentLocal;
import victor.training.jee6.cdi.lifecycle.beans.RequestLocal;
import victor.training.jee6.cdi.lifecycle.beans.SessionLocal;
import victor.training.jee6.cdi.lifecycle.beans.StatelessLocal;

@WebServlet("/lifecycle")
public class LifecycleServlet extends HttpServlet {

	@Inject
	private RequestLocal requestLocal;

	@Inject
	private SessionLocal sessionLocal;

	@Inject
	private SessionLocal statefulSessionLocal;

	@Inject
	private ApplicationLocal applicationLocal;

	@Inject
	private DependentLocal dependentLocal;

	@Inject
	private StatelessLocal statelessLocal;

	@Inject
	private AnEJB anEJB;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String body = hashCode() + "\nIn servlet : ";
		body += requestLocal.getInstanceId() + sessionLocal.getInstanceId() + applicationLocal.getInstanceId()
				+ dependentLocal.getInstanceId() + statefulSessionLocal.getInstanceId()
				+ statelessLocal.getInstanceId();
		body += "\nIn the EJB: " + anEJB.showInjectedDependencies();
		body += "\nThe " + applicationLocal.getInstanceId() + " accessed "
				+ applicationLocal.getInstanceIdOfRequestLocal();

		resp.getWriter().println("<html><body>" + body.replace("\n", "<br />") + "</body></html>");
	}
}
