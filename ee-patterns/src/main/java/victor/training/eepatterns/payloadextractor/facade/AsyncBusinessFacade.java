package victor.training.eepatterns.payloadextractor.facade;

import javax.ejb.Local;

@Local
public interface AsyncBusinessFacade {
	public void consumeTextMessage(String textMessage);

	public void consumeMapMessage(String param1, String param2);
}
