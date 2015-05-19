package victor.training.jee7.jaxrs20;

import java.net.URI;
import java.util.concurrent.ExecutionException;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import victor.training.jee7.executor.MyExecutorClient;

@Path("/rest")
public class MyResource {
	private static final Logger log = LoggerFactory.getLogger(MyResource.class);

	@Inject
	@Named("baseUrl")
	private String baseUrl;
	
	@GET
	public String getData() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(baseUrl+"/rest/customer"); // send a request to my own server
		Form form = new Form()
				.param("name", "Bill")
				.param("product", "IPhone 5")
				.param("CC", "4444 4444 4444 4444");
		Response response = target.request().post(Entity.form(form));
		assert response.getStatus() == 200;
		CustomerXml order = response.readEntity(CustomerXml.class);

		return "Data: " + ReflectionToStringBuilder.toString(order);
	}

	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Path("customer")
	public CustomerXml getData2(@FormParam("name") String name) {
		log.debug("new customer name (form param): {}", name);
		return new CustomerXml("NameLot");
	}

	@GET
	@Path("async")
	public void asyncInvokeServer(@Suspended final AsyncResponse response0) {
		log.debug("Start");
		InvocationCallback<String> callback = new InvocationCallback<String>() {
			@Override
			public void completed(String response) {
				response0.resume(Response.ok("Received response from endpoint2: "+ response).build());
				log.debug("Received response from 2nd endpoint. Resuming the original response which was previously suspended");
			}
			@Override
			public void failed(Throwable throwable) {
			}
		};
		log.debug("Sending async request to 2nd endpoint");
		Client client = ClientBuilder.newClient(); 
		
		URI uri = UriBuilder.fromUri(baseUrl + "/rest/endpoint2/{pathParam}")
				.queryParam("queryParam", "qp")
				.build("pp");
		
		client.target(uri)
		      .request()
		      .async()
		      .get(callback);
		log.debug("Exiting resource method (freeing thread). My current response will remain suspended until I get a response...");
	}

	@GET
	@Path("endpoint2/{pathParam}")
	public String endpoint2(@PathParam("pathParam") String pathParam, @QueryParam("queryParam") String queryParam) throws InterruptedException {
		log.debug("Start 2nd endpoint with pathParam={}, queryParam={}", pathParam, queryParam);
		Thread.sleep(3000);
		log.debug("returning response");
		return "OK. pathParam="+pathParam+", queryParam="+queryParam;
	}
	
	@EJB
	private MyExecutorClient executorPlay;
	
	@GET
	@Path("executor")
	public String testExecutor() throws InterruptedException, ExecutionException {
		log.error("Message");
		return executorPlay.testAll();
	}
}
