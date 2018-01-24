// SOLUTION (
package victor.training.jpa.entity.employee;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYEE_ROLE")
@SequenceGenerator(name="seq", sequenceName="EMPLOYEE_ROLE_SEQ")
public class EmployeeRole extends BaseLabel {

}
