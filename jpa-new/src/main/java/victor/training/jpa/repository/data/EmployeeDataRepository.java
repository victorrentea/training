package victor.training.jpa.repository.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import victor.training.jpa.entity.Employee;

@Repository
public interface EmployeeDataRepository extends JpaRepository<Employee, Integer>, EmployeeDataRepositoryCustom {

	@Query("SELECT e FROM Employee e LEFT JOIN FETCH e.projects")
	public List<Employee> getAllFetchProjects();

	public Employee getById(Integer employeeId);
	
	public Optional<Employee> getByName(Integer employeeId);
	
	public Long countByName(String name);
}
