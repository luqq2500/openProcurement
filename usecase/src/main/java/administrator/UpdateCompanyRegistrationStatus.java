package administrator;

import administrator.api.CompanyRegistrationStatusUpdater;
import administrator.exception.AdministratorNotFoundException;
import administrator.spi.AdministratorRepository;
import company.Company;
import company.CompanyRegistration;
import company.exception.CompanyRegistrationNotFound;
import company.spi.CompanyRegistrationRepository;
import company.spi.CompanyRepository;
import ddd.DomainService;

import java.util.Optional;

@DomainService
public class UpdateCompanyRegistrationStatus implements CompanyRegistrationStatusUpdater {
    private final AdministratorRoles serviceRole = AdministratorRoles.PROCUREMENT_ADMINISTRATOR;
    private final CompanyRegistrationRepository companyRegistrationRepository;
    private final AdministratorRepository administratorRepository;
    private final CompanyRepository companyRepository;

    public UpdateCompanyRegistrationStatus(CompanyRegistrationRepository companyRegistrationRepository, AdministratorRepository administratorRepository, CompanyRepository companyRepository) {
        this.companyRegistrationRepository = companyRegistrationRepository;
        this.administratorRepository = administratorRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public void update(UpdateCompanyRegistrationStatusCommand command) {
        Administrator administrator = findAdministrator(command);
        administrator.getRole().validateAssignedRole(serviceRole);
        CompanyRegistration registration = getCompanyRegistration(command);
        CompanyRegistration updatedRegistration = registration.updateStatus(command.administratorId(), command.status(), command.notes());
        companyRegistrationRepository.add(updatedRegistration);
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
                    updatedRegistration.getAddress());
            companyRepository.add(company);
        }
    }

    private CompanyRegistration getCompanyRegistration(UpdateCompanyRegistrationStatusCommand command) {
        return companyRegistrationRepository.registrations().stream()
                .filter(r -> r.getRegistrationNumber().equals(command.companyRegistrationNumber()))
                .findFirst()
                .orElseThrow(() -> new CompanyRegistrationNotFound("Business registration " + command.companyRegistrationNumber() + " not found"));
    }
}
