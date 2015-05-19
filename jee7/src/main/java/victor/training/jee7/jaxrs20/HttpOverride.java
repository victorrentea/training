package victor.training.jee7.jaxrs20;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Provider
public class HttpOverride implements ContainerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(HttpOverride.class);

	public void filter(ContainerRequestContext ctx) {
		log.debug("Intercepted the request");

		String method = ctx.getHeaderString("X-Http-Method-Override");
		if (method != null)
			ctx.setMethod(method);
	}
}