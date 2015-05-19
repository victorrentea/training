package victor.training.ejb.container.di;

import javax.ejb.Stateless;

@Stateless
public interface MyDependent {

	MyDependency getDependency();

}
