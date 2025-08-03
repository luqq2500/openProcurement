package administrator;
import administrator.api.CompanyRegistrationStatusUpdater;
import administrator.dto.UpdateCompanyRegistrationStatusCommand;
import administrator.exception.AdministratorNotFoundException;
import administrator.spi.AdministratorRepository;
import company.Company;
import company.CompanyRegistration;
import company.exception.CompanyRegistrationNotFound;
import company.spi.CompanyRegistrationRepository;
import company.spi.CompanyRepository;
import notification.NotificationService;
import notification.NotificationCommand;

import java.util.Optional;

public class UpdateCompanyRegistrationStatus implements CompanyRegistrationStatusUpdater {
    private final AdministratorRoles serviceRole = AdministratorRoles.PROCUREMENT_ADMINISTRATOR;
    private final CompanyRegistrationRepository companyRegistrationRepository;
    private final AdministratorRepository administratorRepository;
    private final CompanyRepository companyRepository;
    private final NotificationService notificationService;

    public UpdateCompanyRegistrationStatus(CompanyRegistrationRepository companyRegistrationRepository, AdministratorRepository administratorRepository, CompanyRepository companyRepository, NotificationService notificationService) {
        this.companyRegistrationRepository = companyRegistrationRepository;
        this.administratorRepository = administratorRepository;
        this.companyRepository = companyRepository;
        this.notificationService = notificationService;
    }

    @Override
    public void update(UpdateCompanyRegistrationStatusCommand command) {
        Administrator administrator = findAdministrator(command);
        administrator.getRole().validateAssignedRole(serviceRole);
        CompanyRegistration registration = getCompanyRegistration(command);
        CompanyRegistration updatedRegistration = registration.updateStatus(command.administratorId(), command.status(), command.notes());
        companyRegistrationRepository.add(updatedRegistration);
        NotificationCommand notificationCommand = generateNotificationCommand(command, updatedRegistration);
        notificationService.notify(notificationCommand);
        registerApprovedRegistration(updatedRegistration);
    }

    private Administrator findAdministrator(UpdateCompanyRegistrationStatusCommand command) {
        Optional<Administrator> optionalAdministrator = administratorRepository.findById(command.administratorId());
        if (optionalAdministrator.isEmpty()) {
            throw new AdministratorNotFoundException("Administrator does not exist.");
        }return optionalAdministrator.get();
    }

    private void registerApprovedRegistration(CompanyRegistration updatedRegistration) {
        if (updatedRegistration.getStatus().isApproved()) {
            Company company = new Company(updatedRegistration.getCompanyName(),
                    updatedRegistration.getRegistrationNumber(),
                    updatedRegistration.getTaxNumber(),
                    updatedRegistration.getStructure(),
                    updatedRegistration.getCountryCode());
            companyRepository.add(company);
        }
    }
    private CompanyRegistration getCompanyRegistration(UpdateCompanyRegistrationStatusCommand command) {
        return companyRegistrationRepository.registrations().stream()
                .filter(r -> r.getRegistrationNumber().equals(command.companyRegistrationNumber()))
                .findFirst()
                .orElseThrow(() -> new CompanyRegistrationNotFound("Business registration " + command.companyRegistrationNumber() + " not found"));
    }

    private static NotificationCommand generateNotificationCommand(UpdateCompanyRegistrationStatusCommand command, CompanyRegistration updatedRegistration) {
        String subject = String.format("%s Registration Status: %s", updatedRegistration.getCompanyName(), updatedRegistration.getStatus());
        String message = String.format(
                "Dear Applicant,\n\nThe registration status for %s has been updated to %s.\nAdministrator Notes: %s\n\nPlease contact support if you have any questions.",
                updatedRegistration.getCompanyName(),
                updatedRegistration.getStatus(),
                command.notes() != null ? command.notes() : "No additional notes provided"
        );
        return new NotificationCommand(updatedRegistration.getApplicant().email(), subject, message);
    }
}
