// SOLUTION (
package victor.training.jpa.entity.employee;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DOMAIN_AREA")
@SequenceGenerator(name="seq", sequenceName="DOMAIN_AREA_SEQ")
public class DomainArea extends BaseLabel {

}
