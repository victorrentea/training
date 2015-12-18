package batch.services;

import java.util.Date;
import java.util.Set;

import batch.model.domain.Appointment;
import batch.model.domain.AppointmentRegistration;
import batch.model.domain.Consumer;
import batch.model.domain.ServicePlatform;
import batch.model.domain.TimeRange;



public interface ConsumerService {

    Consumer getConsumerEager(Long id);

    Consumer getConsumerByAppointmentRegistrationId(Long registrationId);

    Consumer saveConsumer(Consumer consumer);

    Consumer saveFullConsumerWithEmailAndLanguageChange(Consumer consumer, String newEmail, String newLanguage);

    Consumer registerAppointmentRegistration(Consumer consumer,
            AppointmentRegistration appointmentRegistrationToRegister);

    Consumer cancelAppointmentRegistration(Consumer consumer, AppointmentRegistration appointmentRegistrationToCancel,
            boolean superUser);

    Consumer cancelSingleAppointment(Consumer consumer, Appointment appointmentToCancel);

    /**
     * Finds the {@link Consumer} with the corresponding alternativeKey.
     * 
     * @param alternativeKey
     * @return the consumer, or null if not found.
     */
    Consumer findByAlternativeKey(String alternativeKey);

    Consumer createLightConsumerWithAppointmentRegistrations(Consumer consumer,
            Set<AppointmentRegistration> appointmentRegistrations);

    Consumer getConsumerByIdentityId(String id);

    Consumer cancelAppointmentRegistrationKeepingShipments(long cId, long arId, Date startDate);

    Consumer registerAppointmentRegistration(Long cId, AppointmentRegistration ar);

    Consumer moveAppointmentRegistration(Long cId, long arOldId, Date startDate, AppointmentRegistration arNew);

    Consumer cancelSingleAppointment(Long cId, Date day, TimeRange timeRange);

    Consumer moveAppointment(long cId, Date fromDate, TimeRange fromTimeRange, Date toDate, TimeRange toTimeRange);

    /**
     * Checks whether:
     * <ul>
     * <li>A {@link ServicePlatform} is found for the {@link Consumer}</li>
     * <li>The given day and serviceSlot are feasible for the linked {@link ServicePlatform}</li>
     * </ul>
     * 
     * @param consumer
     *            The consumer to do the check for
     * @param day
     *            The day of the serviceSlot to check
     * @param serviceSlot
     *            The time range of the serviceSlot to check
     * @return True if all conditions are met, false otherwise
     */
    boolean checkServiceSlotFeasibleForConsumer(Consumer consumer, Date day, TimeRange serviceSlot);

    /**
     * Creates a new 'one shot' {@link Appointment} for the given {@link Consumer} and time.
     * {@link ConsumerService#checkServiceSlotFeasibleForConsumer(Consumer, Date, TimeRange)} should be called before
     * this method.
     * 
     * @param consumer
     *            To create the appointment for
     * @param day
     *            The day of the selected serviceSlot
     * @param serviceSlot
     *            The time range of the selected serviceSlot
     * @return The newly created Appointment
     */
    Appointment createAppointmentOnServiceSlot(Consumer consumer, Date day, TimeRange serviceSlot);

    boolean hasShipments(String consumerId);

    void saveConsumerWithAppointmentRegistrations(Consumer c, Set<AppointmentRegistration> ars);

    void cancelAppointmentWithoutShipments(Consumer consumer, Appointment appointmentRegistration);

    void sendConfirmationEmail(Consumer consumer);

    void activateConsumer(Consumer consumer);

    void deActivateConsumer(Consumer consumer);

    void auditConsumerLogin(Consumer consumer);
    
	void createConsumer(Consumer consumer);

	Appointment getConsumerAppointmentAtDate(Long consumerId, Date date);

	Consumer getConsumerByEmail(String email);
}
