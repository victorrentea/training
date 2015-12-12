package victor.training.spring.webapp;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Provider;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceSingleton {

	private UserSession userSession;
	
	@Autowired
	private PrototypeStatefulBean prototypeInjected;

	@Autowired
	private Provider<PrototypeStatefulBean> prototypeProvider;
	
	@Autowired
	private ObjectFactory<PrototypeStatefulBean> prototypeFactory;

	public String getUsername() {
		return userSession.getUsername();
	}
	
	public void loginUser(String username) {
		userSession.setUsername(username);
		userSession.setPremium(true); // retrieve user details from DB
	}

	public void setUserSession(UserSession userDetails) {
		this.userSession = userDetails;
	}
	
	public String testInjectedPrototype() {
		String result = "Initial="+prototypeInjected.getState();
		prototypeInjected.setState("state of " + new SimpleDateFormat("hh:mm:ss").format(new Date()));
		result += ", final=" + prototypeInjected.getState();
		return result;
	}
	
	public String testProvidedPrototype() {
		PrototypeStatefulBean prototype = prototypeProvider.get();
		String result = "Initial="+prototype.getState();
		prototype.setState("state of " + new SimpleDateFormat("hh:mm:ss").format(new Date()));
		result += ", final=" + prototype.getState();
		return result;
	}
	
	public String testFactoryPrototype() {
		PrototypeStatefulBean prototype = prototypeFactory.getObject();
		String result = "Initial="+prototype.getState();
		prototype.setState("state of " + new SimpleDateFormat("hh:mm:ss").format(new Date()));
		result += ", final=" + prototype.getState();
		return result;
	}
	

}
