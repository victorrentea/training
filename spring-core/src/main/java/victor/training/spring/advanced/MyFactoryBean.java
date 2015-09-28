package victor.training.spring.advanced;

import org.springframework.beans.factory.FactoryBean;

public class MyFactoryBean implements FactoryBean<String> {

	@Override
	public String getObject() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}

}
