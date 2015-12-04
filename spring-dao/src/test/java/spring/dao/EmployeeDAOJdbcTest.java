package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class EmployeeDAOJdbcTest extends EmployeeDAOTest {

	@Autowired
	@Qualifier("employeeDaoJdbc")
	private EmployeeDAO dao;

	@Override
	public EmployeeDAO getDao() {
		return dao;
	}
}
