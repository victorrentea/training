package victor.training.eepatterns.locator;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import victor.training.eepatterns.soa.customer.facade.CustomerFacade;

@WebServlet("/ServiceLocator")
public class ServiceLocatorTest extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		CustomerFacade service = ServiceLocator.getInstance().getCustomerService();
		assertNotNull(service);
		System.out.println("Test OK");
	}

}
