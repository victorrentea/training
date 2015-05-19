package victor.training.jee6.cdi.di;

import javax.inject.Inject;

import victor.training.jee6.cdi.di.dependencies.BasicPOJO;
import victor.training.jee6.cdi.di.dependencies.CtrPOJO;
import victor.training.jee6.cdi.di.dependencies.InitPOJO;
import victor.training.jee6.cdi.di.dependencies.MyService;
import victor.training.jee6.cdi.di.qualifier.Color;
import victor.training.jee6.cdi.di.qualifier.ColorQualifier;

public class Dependent {
	@Inject
	private BasicPOJO basicPOJO;

	private final InitPOJO initPOJO;

	private CtrPOJO ctrPOJO;

	@Inject
	@ColorQualifier(Color.RED)
	private MyService myService;

	@Inject
	public Dependent(InitPOJO initPOJO) {
		this.initPOJO = initPOJO;
	}
	
	public void doStuffWithDependencies() {
		basicPOJO.doStuff();
		initPOJO.doStuff();
		ctrPOJO.doStuff();
		myService.doStuff();
	}

	@Inject
	public void setCtrPOJO(CtrPOJO ctrPOJO) {
		this.ctrPOJO = ctrPOJO;
	}
	
	@Override
	public String toString() {
		return initPOJO.toString();
	}
}
