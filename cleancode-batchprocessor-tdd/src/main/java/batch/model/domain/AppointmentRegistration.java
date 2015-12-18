package batch.model.domain;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import batch.util.DateUtils;



public class AppointmentRegistration extends BaseEntity {

	private DateRange validityPeriod;

	private TimeRange serviceSlot;

	private Address address = new Address();

	private String remarks;

	private Date nextAppointmentGenerationStartDate;

	private boolean conflict;

	private Map<Date, Appointment> appointments = new HashMap<Date, Appointment>();

	private Date forceAppointmentGenerationDate;

	protected AppointmentRegistration() {
	}

	public AppointmentRegistration(Date startDate, TimeRange serviceSlot, Address address, String remarks) {
		this.validityPeriod = new DateRange(startDate, null);
		this.serviceSlot = serviceSlot;
		this.address = address;
		this.remarks = remarks;
	}

	public DateRange getValidityPeriod() {
		return validityPeriod;
	}

	public Date getStartDate() {
		return validityPeriod.getMin();
	}

	public void setStartDate(Date startDate) {
		if (this.validityPeriod == null) {
			this.validityPeriod = new DateRange(startDate, null);
		}
		this.validityPeriod.setMin(startDate);
	}

	public String getStartDateString() {
		return getStartDate() == null ? null : DateUtils.format(getStartDate());
	}

	public void setStartDateString(String dateStr) {
		setStartDate(dateStr == null ? null : DateUtils.parseDate(dateStr));
	}

	public Date getEndDate() {
		return this.validityPeriod.getMax();
	}

	public void setEndDate(Date endDate) {
		this.validityPeriod.setMax(endDate);
	}

	public TimeRange getServiceSlot() {
		return serviceSlot;
	}

	public void setServiceSlot(TimeRange serviceSlot) {
		this.serviceSlot = serviceSlot;
	}

	public Address getAddress() {
		return address;
	}

	public String getRemarks() {
		return remarks;
	}

	public boolean isConflict() {
		return conflict;
	}

	public void setConflict(boolean conflict) {
		this.conflict = conflict;
	}

	public void addAppointment(Appointment appointment) {
		appointments.put(appointment.getDate(), appointment);
	}

	public void removeAppointment(Appointment appointment) {
		appointments.remove(appointment.getDate());
	}

	public Map<Date, Appointment> getAppointments() {
		return Collections.unmodifiableMap(appointments);
	}

	public Date getNextAppointmentGenerationStartDate() {
		return nextAppointmentGenerationStartDate;
	}

	public void setNextAppointmentGenerationStartDate(Date nextAppointmentGenerationStartDate) {
		if (nextAppointmentGenerationStartDate == null) {
			this.nextAppointmentGenerationStartDate = nextAppointmentGenerationStartDate;
		} else {
			this.nextAppointmentGenerationStartDate = (Date) nextAppointmentGenerationStartDate.clone();
		}
	}

	public boolean startsBefore(AppointmentRegistration other) {
		return DateUtils.compareDates(getStartDate(), other.getStartDate()) < 0;
	}

	public Appointment getAppointment(Appointment appointment) {
		for (Appointment a : appointments.values()) {
			if (a.equals(appointment)) {
				return a;
			}
		}
		return null;
	}

	public Appointment getFirstAppointment() {
		Appointment firstAppointment = null;
		for (Appointment appointment : getAppointments().values()) {
			if (firstAppointment == null
					|| DateUtils.compareDates(appointment.getDate(), firstAppointment.getDate()) < 0) {
				firstAppointment = appointment;
			}
		}
		return firstAppointment;
	}

	public Set<Appointment> getAppointmentsStartingFrom(Date date) {
		Set<Appointment> result = new HashSet<Appointment>();
		if (appointments != null && appointments.size() > 0) {
			for (Date appointmentDate : appointments.keySet()) {
				if (DateUtils.compareDatesWithoutTime(date, appointmentDate) <= 0) {
					result.add(appointments.get(appointmentDate));
				}
			}
		}
		return result;
	}

	public void removeAllAppointments() {
		appointments.clear();
	}

	public Date getForceAppointmentGenerationDate() {
		return forceAppointmentGenerationDate;
	}

	public void setForceAppointmentGenerationDate(Date forceAppointmentGenerationDate) {
		this.forceAppointmentGenerationDate = (Date) forceAppointmentGenerationDate.clone();
	}
}
