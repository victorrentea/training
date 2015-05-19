package victor.training.ejb.container.tx;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;

import victor.training.ejb.container.tx.model.Notification;

@Stateless
public class NotificationDAOBean implements NotificationDAO {

	// Hack ONLY FOR TEST
	public static EntityManager em;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void persist(Notification notification) {
		em.persist(notification);
	}

}
