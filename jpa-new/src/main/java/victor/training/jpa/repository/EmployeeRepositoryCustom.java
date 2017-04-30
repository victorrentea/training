package victor.training.jpa.repository;

import java.util.List;

import victor.training.jpa.entity.Employee;
import victor.training.jpa.entity.search.EmployeeSearchCriteria;

public interface EmployeeRepositoryCustom {

	List<Employee> search(EmployeeSearchCriteria criteria);

}
