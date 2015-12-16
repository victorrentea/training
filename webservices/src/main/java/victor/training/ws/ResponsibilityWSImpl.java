package victor.training.ws;

import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import victor.training.ws.responsibility.impl.ResponsibilitySEI;
import victor.training.ws.responsibility.impl.dto.ResponsibilityXml;
import victor.training.ws.responsibility.impl.messages.GetUserResponsibilityRequest;
import victor.training.ws.responsibility.impl.messages.GetUserResponsibilityResponse;

@WebService
@HandlerChain(file = "/my-handlers.xml")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class ResponsibilityWSImpl implements ResponsibilitySEI {

	@Override
	public GetUserResponsibilityResponse getUserResponsibility(GetUserResponsibilityRequest parameters) {
		GetUserResponsibilityResponse response = new GetUserResponsibilityResponse();
		ResponsibilityXml responsibilityXml = new ResponsibilityXml();
		responsibilityXml.setBehavior("ADMIN");
		response.setResponsibility(responsibilityXml);
		return response;
	}

}
