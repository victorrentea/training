package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class EmployeeDAOJpaTest extends EmployeeDAOTest{

	@Autowired
	@Qualifier("employeeDaoJpa")
	private EmployeeDAO dao;
	
	@Override
	protected EmployeeDAO getDao() {
		return dao;
	}
}
