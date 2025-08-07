package administrator;

import administrator.api.CompanyRegistrationStatusUpdater;
import administrator.spi.AdministratorRepository;
import company.Company;
import company.CompanyRegistration;
import company.spi.CompanyRegistrationRepository;
import company.spi.CompanyRepository;
import ddd.DomainService;


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
        Administrator administrator = administratorRepository.getById(command.administratorId());
        administrator.getRole().validateAssignedRole(serviceRole);
        CompanyRegistration registration = companyRegistrationRepository.getById(command.companyRegistrationId());
        CompanyRegistration updatedRegistration = registration.updateStatus(command.administratorId(), command.status(), command.notes());
        companyRegistrationRepository.add(updatedRegistration);
        registerApprovedRegistration(updatedRegistration);
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
}
