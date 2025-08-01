package administrator;
import administrator.api.CompanyRegistrationStatusUpdater;
import administrator.dto.UpdateCompanyRegistrationStatusCommand;
import administrator.spi.AdministratorRepository;
import company.CompanyRegistration;
import company.spi.CompanyRegistrationRepository;

public class UpdateCompanyRegistrationStatus implements CompanyRegistrationStatusUpdater {
    private final CompanyRegistrationRepository companyRegistrationRepository;
    private final AdministratorRepository administratorRepository;
    private final AdministratorRoles serviceRole = AdministratorRoles.PROCUREMENT_ADMINISTRATOR;

    public UpdateCompanyRegistrationStatus(CompanyRegistrationRepository companyRegistrationRepository, AdministratorRepository administratorRepository) {
        this.companyRegistrationRepository = companyRegistrationRepository;
        this.administratorRepository = administratorRepository;
    }

    @Override
    public void update(UpdateCompanyRegistrationStatusCommand command) {
        Administrator administrator = findAdministrator(command);
        administrator.getRole().validateAssignedRole(serviceRole);
        CompanyRegistration registration = getCompanyRegistration(command);
        CompanyRegistration updatedRegistration = registration.updateStatus(command.administratorId(), command.status(), command.notes());
    }

    private CompanyRegistration getCompanyRegistration(UpdateCompanyRegistrationStatusCommand command) {
        return companyRegistrationRepository.registrations().stream()
                .filter(r -> r.getRegistrationNumber().equals(command.companyRegistrationNumber()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Business registration " + command.companyRegistrationNumber() + " not found"));
    }

    private Administrator findAdministrator(UpdateCompanyRegistrationStatusCommand command) {
        return administratorRepository.administrators().stream()
                .filter(a -> a.getAdministratorId().equals(command.administratorId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Administrator not found"));
    }
}
