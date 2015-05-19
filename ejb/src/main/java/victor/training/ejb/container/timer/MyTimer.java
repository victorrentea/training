package victor.training.ejb.container.timer;

import javax.ejb.Local;

@Local
public interface MyTimer {

	void setTimer(Long businessId);
}
