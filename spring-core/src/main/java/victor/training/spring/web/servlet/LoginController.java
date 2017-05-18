package victor.training.spring.web.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import victor.training.spring.web.service.UserService;

// WARNING: DON'T DO SUCH A HORRID THING IN PRODUCTION. Just for playing...
public class LoginController {

	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private ServletContext context;

	public LoginController(HttpServletRequest request, HttpServletResponse response, ServletContext context) {
		this.request = request;
		this.response = response;
		this.context = context;
	}

	public void service() throws IOException, ServletException {
		if (isUserLoggedIn()) {
			serviceLoggedInUser();
		} else {
			serviceNewUser();
		}
	}

	private boolean isUserLoggedIn() {
		return request.getSession(false) != null;
	}

	private void serviceNewUser() throws IOException, ServletException {
		if (userWantsToLogin()) {
			loginUser();
			redirectToHome();
		} else {
			forward("/WEB-INF/loggedOut.jsp");
		}
	}

	private boolean userWantsToLogin() {
		return request.getParameter("login") != null;
	}

	private void redirectToHome() throws IOException {
		response.sendRedirect("home");
	}

	private void loginUser() {
		request.getSession(true);
		ApplicationContext appCtx = WebApplicationContextUtils.getWebApplicationContext(context);
		appCtx.getBean(UserService.class).loginUser(request.getParameter("username"));
	}

	private void forward(String path) throws ServletException, IOException {
		request.getRequestDispatcher(path).forward(request, response);
	}

	private void serviceLoggedInUser() throws IOException, ServletException {
		if (userWantsToLogout()) {
			logoutUser();
			redirectToHome();
		} else {
			forward("/WEB-INF/loggedIn.jsp");
		}
	}

	private boolean userWantsToLogout() {
		return request.getParameter("logout") != null;
	}

	private void logoutUser() {
		request.getSession(false).invalidate();
	}

}
