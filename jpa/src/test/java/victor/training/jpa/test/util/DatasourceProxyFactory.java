package victor.training.jpa.test.util;

import javax.sql.DataSource;

import org.springframework.beans.factory.FactoryBean;

import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;

public class DatasourceProxyFactory implements FactoryBean<DataSource> {
	
	private final DataSource actualDataSource;
	
	public DatasourceProxyFactory(DataSource actualDataSource) {
		this.actualDataSource = actualDataSource;
	}

	@Override
	public DataSource getObject() throws Exception {
	    return ProxyDataSourceBuilder
	        .create(actualDataSource)
	        .name("datasource-proxy")
	        .multiline()
	        .logQueryToSysOut()
	        .countQuery()
	        .build();
	}

	@Override
	public Class<?> getObjectType() {
		return DataSource.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
