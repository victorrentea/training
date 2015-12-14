package victor.training.ws.responsibility.impl;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public class JavaFirstWebService {

	@WebMethod
	public int sum(int a, int b) {
		return a + b;
	}
}
