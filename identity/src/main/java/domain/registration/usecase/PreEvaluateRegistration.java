package domain.registration.usecase;

import domain.employee.spi.EmployeeRepository;
import domain.registration.RegistrationApplicationStatus;
import domain.registration.api.RegistrationPreEvaluator;
import domain.registration.events.domain.RegistrationPreEvaluated;
import domain.registration.spi.RegistrationRepository;
import event.EventBus;

import java.util.UUID;

public class PreEvaluateRegistration implements RegistrationPreEvaluator {
    private final EmployeeRepository employeeRepository;
    private final RegistrationRepository registrationRepository;
    private final EventBus eventBus;

    public PreEvaluateRegistration(EmployeeRepository employeeRepository, RegistrationRepository registrationRepository, EventBus eventBus) {
        this.employeeRepository = employeeRepository;
        this.registrationRepository = registrationRepository;
        this.eventBus = eventBus;
    }

    @Override
    public void evaluate(UUID registrationId, String companyName, String brn, String username) {
        if (registrationRepository.findByCompanyName(companyName).isPresent()
                || registrationRepository.findByBrn(brn).isPresent()
                || employeeRepository.findByUsername(username).isPresent()) {
            registrationRepository.get(registrationId).updateStatus(RegistrationApplicationStatus.REJECTED);
            eventBus.publish(new RegistrationPreEvaluated(registrationId, RegistrationApplicationStatus.REJECTED));
        } else {
            registrationRepository.get(registrationId).updateStatus(RegistrationApplicationStatus.APPROVED);
            eventBus.publish(new RegistrationPreEvaluated(registrationId, RegistrationApplicationStatus.APPROVED));
        }
    }
}