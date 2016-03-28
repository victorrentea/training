package spring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import spring.model.Employee;

public class EmployeeDAOJdbc implements EmployeeDAO {

	// private SimpleJdbcTemplate jdbcTemplate;
	private DataSource dataSource;

	public EmployeeDAOJdbc(DataSource dataSource) {
		// this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
		this.dataSource = dataSource;
	}

	@Override
	public void persist(Employee employee) {
		String sql = "INSERT INTO employee(id, name, phone) VALUES (?, ?, ?)";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, employee.getId());
			ps.setString(2, employee.getName());
			ps.setString(3, employee.getPhone());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO: something, don't know what..
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO: something, don't know what..
				}
			}
		}

		// jdbcTemplate.update("", x, y, z);
		
	}

	@Override
	public void removeById(String employeeId) {
		String sql = "DELETE FROM employee WHERE id = ?";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, employeeId);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO: something, don't know what..
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO: something, don't know what..
				}
			}
		}
		// jdbcTemplate.update(sql, employeeId);
	}

	@Override
	public void update(Employee employee) {
		String sql = "UPDATE employee SET name = :name, phone = :phone WHERE id = :id";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, employee.getName());
			ps.setString(2, employee.getPhone());
			ps.setString(3, employee.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO: something, don't know what..
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO: something, don't know what..
				}
			}
		}

		// Map<String, Object> args = new HashMap<String, Object>();
		// jdbcTemplate.update("sql using :namedParam", args);
	}

	@Override
	public Employee getById(String employeeId) {
		String sql = "SELECT id, name, phone FROM employee WHERE id = ?";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, employeeId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Employee employee = new Employee();
				employee.setId(rs.getString(1));
				employee.setName(rs.getString(2));
				employee.setPhone(rs.getString(3));
				return employee;
			}
		} catch (SQLException e) {
			// TODO: something, don't know what..
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO: something, don't know what..
				}
			}
		}
		return null;
		// return jdbcTemplate.queryForObject(sql, new RowMapper<Employee>() {
		// }, employeeId);
	}
}
