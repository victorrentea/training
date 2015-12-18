package batch.model.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

public class Consumer extends BaseEntity {

	private String firstName;
	
	private String lastName;
	
	private String phoneNumber;
	
	private String emailAddress;

	private Address address = new Address();

	private Status status = Status.ACTIVATED;

	private final Set<AppointmentRegistration> appointmentRegistrations = new HashSet<AppointmentRegistration>();

	public boolean isOverlappingExistingAppointment(AppointmentRegistration appointmentRegistration) {
		return CollectionUtils.isNotEmpty(getOverlappingAppointmentRegistrationsFor(appointmentRegistration));
	}

	public Set<AppointmentRegistration> getOverlappingAppointmentRegistrationsFor(
			AppointmentRegistration appointmentRegistration) {
		return null;
	}

	public void addAppointmentRegistration(AppointmentRegistration appointmentRegistration) {
		if (appointmentRegistration == null) {
			throw new IllegalArgumentException("appointmentRegistration must not be null");
		} else if (isOverlappingExistingAppointment(appointmentRegistration)) {
			throw new IllegalArgumentException(
					"Overlapping appointmentRegistration not allowed: Please merge them properly");
		}
		appointmentRegistrations.add(appointmentRegistration);
	}

	public Set<AppointmentRegistration> getAppointmentRegistrations() {
		return Collections.unmodifiableSet(appointmentRegistrations);
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public boolean isActivated() {
		return Status.ACTIVATED == getStatus();
	}

}