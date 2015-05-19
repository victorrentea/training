package victor.training.jee6.cdi.di.dependencies;

import javax.enterprise.inject.Produces;

public class POJOFactory {

	@Produces
	public CtrPOJO createCtrPOJO() {
		return new CtrPOJO("key parameter");
	}

	@Produces
	public InitPOJO createInitPOJO() {
		InitPOJO i = new InitPOJO();
		System.out.println("Creating instance manual;ly");
		i.init("key parameter");
		return i;
	}
}
