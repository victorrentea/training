package spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import spring.model.Employee;

public class EmployeeDAOJdbc_Solved implements EmployeeDAO {
		
	private SimpleJdbcTemplate jdbcTemplate;
	
	public EmployeeDAOJdbc_Solved(DataSource dataSource) {
		this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	@Override
	public void persist(Employee employee) {
		jdbcTemplate.update(
				"INSERT INTO employee(id, name, phone) VALUES (?, ?, ?)",
				employee.getId(),
				employee.getName(),
				employee.getPhone()
				);
	}
	
	@Override
	public void update(Employee employee) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", employee.getId());
		paramMap.put("name", employee.getName());
		paramMap.put("phone", employee.getPhone());
		
		jdbcTemplate.update(
				"UPDATE employee " +
				"SET NAME = :name, PHONE = :phone " +
				"WHERE ID = :id", 
				paramMap
				);
	}

	@Override
	public void removeById(String employeeId) {
		jdbcTemplate.update(
				"DELETE FROM employee " +
				"WHERE id = ?", 
				employeeId
				);
	}
	
	@Override
	public Employee getById(String employeeId) {
		return jdbcTemplate.queryForObject(
				"SELECT id, name, phone FROM employee WHERE id = ?", 
				new RowMapper<Employee>() {
					@Override
					public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
						Employee employee = new Employee();
						employee.setId(rs.getString(1));
						employee.setName(rs.getString(2));
						employee.setPhone(rs.getString(3));
						return employee;
					}
				},
				employeeId);
	}
	
}
