package victor.training.spring.dao;

import javax.sql.DataSource;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseDao {
	@Autowired // SOLUTION
	protected DataSource dataSource;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
