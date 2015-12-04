package victor.mockito.ee;

import java.util.List;

public class MyService {

	public MyDao myDao;
	
	public void increaseSallaries() {
		List<Employee> employees = myDao.getAllEmployees();
		
		for (Employee employee : employees) {
			employee.setSallary(employee.getSallary() * 2);
		}
	}
}
