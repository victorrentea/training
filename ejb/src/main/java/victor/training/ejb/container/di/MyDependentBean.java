package victor.training.ejb.container.di;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class MyDependentBean implements MyDependent {

	@EJB
	private MyDependency dependency;
	
	@Override
	public MyDependency getDependency() {
		return dependency;
	} 
}
