package victor.training.jee7;

import java.io.IOException;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(value = "/*", asyncSupported = true)
public class ExtractDeploymentAddressFilter implements Filter {

	private static String DEPLOY_BASE_URL;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Produces
	@Named("baseUrl")
	public String getBaseUrl() {
		return DEPLOY_BASE_URL;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		if (DEPLOY_BASE_URL == null) {
			DEPLOY_BASE_URL = "http://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getServletContext().getContextPath();
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
