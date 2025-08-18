package domain.registration.usecases;

import domain.company.spi.CompanyRepository;
import domain.employee.spi.EmployeeRepository;
import domain.registration.RegistrationApplication;
import domain.registration.RegistrationApplicationStatus;
import domain.registration.events.RegistrationPreEvaluationPassed;
import domain.registration.api.RegistrationPreEvaluator;
import domain.registration.spi.RegistrationRepository;
import event.EventBus;
import port.IntegrationEventHandler;

import java.util.UUID;

public class PreEvaluateRegistration implements RegistrationPreEvaluator {
    private final RegistrationRepository registrationRepository;
    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final EventBus eventBus;
    private final IntegrationEventHandler<RegistrationPreEvaluationPassed> integrationEventHandler;

    public PreEvaluateRegistration(RegistrationRepository registrationRepository, EmployeeRepository employeeRepository, CompanyRepository companyRepository, EventBus eventBus, IntegrationEventHandler<RegistrationPreEvaluationPassed> integrationEventHandler) {
        this.registrationRepository = registrationRepository;
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
        this.eventBus = eventBus;
        this.integrationEventHandler = integrationEventHandler;
    }

    @Override
    public void evaluate(UUID registrationId, String companyName, String brn, String username) {
        RegistrationApplication registration = registrationRepository.get(registrationId);
        if (registrationRepository.findByCompanyName(companyName).isPresent() ||
                registrationRepository.findByBrn(brn).isPresent() ||
                companyRepository.findByBrn(username).isPresent() ||
                employeeRepository.findByUsername(username).isPresent()) {
            registration.updateStatus(RegistrationApplicationStatus.REJECTED);
            registrationRepository.add(registration);
        }
        registration.updateStatus(RegistrationApplicationStatus.APPROVED);
        registrationRepository.add(registration);
        integrationEventHandler.handle(new RegistrationPreEvaluationPassed(registration.getId(), registration.getCompanyDetails()));
    }
}