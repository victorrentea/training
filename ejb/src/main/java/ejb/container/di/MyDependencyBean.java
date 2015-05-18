package ejb.container.di;

import javax.ejb.Stateless;

@Stateless
public class MyDependencyBean implements MyDependency {

	@Override
	public void method() {
	}
}
