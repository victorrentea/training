package victor.training.ws.responsibility.impl;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import victor.training.ws.responsibility.impl.dto.ResponsibilityXml;
import victor.training.ws.responsibility.impl.messages.GetUserResponsibilityRequest;
import victor.training.ws.responsibility.impl.messages.GetUserResponsibilityResponse;

@WebService
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
