package victor.training.eepatterns.payloadextractor.facade;

import javax.ejb.Stateless;

@Stateless
public class AsyncBusinessFacadeBean implements AsyncBusinessFacade {
	@Override
	public void consumeTextMessage(String textMessage) {

		System.out.println("Consume text");
	}

	@Override
	public void consumeMapMessage(String param1, String param2) {

		System.out.println("Consume object");
	}

}
