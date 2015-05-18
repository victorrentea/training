package ejb.container.di;

import javax.ejb.Stateless;

@Stateless
public interface MyDependent {

	MyDependency getDependency();

}
