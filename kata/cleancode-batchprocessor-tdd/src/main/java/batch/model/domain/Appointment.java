package batch.model.domain;

import java.util.Comparator;
import java.util.Date;

import batch.util.DateUtils;



public class Appointment extends BaseEntity {

	private Date date;

	private AppointmentRegistration appointmentRegistration;
	
	public Appointment() {
	}

	public Appointment(Date appointmentDate) {
		date = appointmentDate;
	}

	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
	    this.date = (Date) date.clone();
	}


	public AppointmentRegistration getAppointmentRegistration() {
		return appointmentRegistration;
	}

	public void setAppointmentRegistration(AppointmentRegistration appointmentRegistration) {
		this.appointmentRegistration = appointmentRegistration;
	}
	
	public static class AppointmentComparator implements Comparator<Appointment> {
		@Override
		public int compare(Appointment first, Appointment second) {
			int result = DateUtils.compareDates(first.getDate(), second.getDate());
			if (result == 0) {
				result = first.getAppointmentRegistration().getServiceSlot()
						.compareTo(second.getAppointmentRegistration().getServiceSlot());
			}
			return result;
		}
	}
}
