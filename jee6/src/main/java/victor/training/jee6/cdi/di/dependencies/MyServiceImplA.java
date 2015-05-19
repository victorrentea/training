package victor.training.jee6.cdi.di.dependencies;

import victor.training.jee6.cdi.di.qualifier.AQualifier;

@AQualifier
public class MyServiceImplA implements MyService {

	@Override
	public void doStuff() {
		System.out.println("MyServiceImplA does stuff");
	}

}
