package victor.training.jee6.cdi.di.dependencies;

import victor.training.jee6.cdi.di.qualifier.Color;
import victor.training.jee6.cdi.di.qualifier.ColorQualifier;

@ColorQualifier(Color.RED)
public class MyServiceImplB implements MyService {

	@Override
	public void doStuff() {
		System.out.println("MyServiceImplB does stuff");
	}

}
