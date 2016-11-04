package victor.training.jpa.repository.data;

import java.util.List;

import victor.training.jpa.entity.employee.Employee;

public interface EmployeeDataRepositoryCustom {

	List<Employee> search(String name, String siteName);

}
