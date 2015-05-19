package victor.training.eepatterns.legacypojo;

import java.util.UUID;

import javax.enterprise.inject.Produces;

public class POJOFactory {

	@Produces
	public POJOAsCDIWithFactory createInstance() {
		return new POJOAsCDIWithFactory(UUID.randomUUID().toString());
	}
}
