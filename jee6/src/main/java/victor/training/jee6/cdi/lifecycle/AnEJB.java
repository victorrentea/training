package victor.training.jee6.cdi.lifecycle;

import javax.ejb.Stateless;
import javax.inject.Inject;

import victor.training.jee6.cdi.lifecycle.beans.ApplicationLocal;
import victor.training.jee6.cdi.lifecycle.beans.RequestLocal;
import victor.training.jee6.cdi.lifecycle.beans.SessionLocal;
import victor.training.jee6.cdi.lifecycle.beans.StatelessLocal;

@Stateless
public class AnEJB {

	@Inject
	private RequestLocal requestLocal;

	@Inject
	private SessionLocal sessionLocal;

	@Inject
	private ApplicationLocal applicationLocal;

	@Inject
	private SessionLocal statefulSessionLocal;

	@Inject
	private StatelessLocal statelessLocal;

	public String showInjectedDependencies() {
		return requestLocal.getInstanceId() + sessionLocal.getInstanceId() + applicationLocal.getInstanceId()
				+ statefulSessionLocal.getInstanceId() + statelessLocal.getInstanceId();

	}

}
