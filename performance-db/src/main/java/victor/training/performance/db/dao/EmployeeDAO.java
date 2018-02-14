package victor.training.performance.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import victor.training.performance.db.domain.Employee;

@Repository
public class EmployeeDAO {

	// private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;

	@Autowired
	public EmployeeDAO(DataSource dataSource) {
		// this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dataSource = dataSource;
	}

	
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
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO: something, don't know what..
					throw new RuntimeException(e);
				}
			}
		}

		// jdbcTemplate.update("", x, y, z);
		
	}

	
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
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO: something, don't know what..
					throw new RuntimeException(e);
				}
			}
		}
		// jdbcTemplate.update(sql, employeeId);
	}

	
	public void update(Employee employee) {
		String sql = "UPDATE employee SET name = ?, phone = ? WHERE id = ?";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
//			conn = DataSourceUtils.getConnection(dataSource);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, employee.getName());
			ps.setString(2, employee.getPhone());
			ps.setString(3, employee.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO: something, don't know what..
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO: something, don't know what..
					throw new RuntimeException(e);
				}
			}
		}

		// Map<String, Object> args = new HashMap<String, Object>();
		// jdbcTemplate.update("sql using :namedParam", args);
	}

	@Transactional(propagation = Propagation.SUPPORTS) // ! Needed for SPring to Proxy this method. The proxy will kill the Tx when you exit on Exception
	public Employee getById(String employeeId) {
		Employee employee = findById(employeeId);
		if (employee == null) {
			throw new RuntimeException("Employee not found for id " + employeeId);
		}
		return employee;
	}
	
	/** @return null when not found */
	public Employee findById(String employeeId) {
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
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {	
					// TODO: something, don't know what..
					throw new RuntimeException(e);
				}
			}
		}
		return null;
		// return jdbcTemplate.queryForObject(sql, new RowMapper<Employee>() {
		// }, employeeId);
	}


	public int countByPhone(String newPhone) {
		String sql = "SELECT count(*) FROM employee WHERE phone = ?";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, newPhone);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			// TODO: something, don't know what..
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {	
					// TODO: something, don't know what..
					throw new RuntimeException(e);
				}
			}
		}
		// return jdbcTemplate.queryFor...
	}
}
