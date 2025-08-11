package administrator;

import administrator.api.CompanyRegistrationStatusUpdater;
import administrator.spi.AdministratorRepository;
import company.Company;
import company.CompanyRegistration;
import company.spi.CompanyRegistrationRepository;
import company.spi.CompanyRepository;
import ddd.DomainService;

import java.time.Instant;


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
    public UpdateCompanyRegistrationStatusResponse update(UpdateCompanyRegistrationStatusRequest request) {
        Administrator administrator = administratorRepository.getById(request.administratorId());
        administrator.validateRole(serviceRole);
        CompanyRegistration registration = companyRegistrationRepository.getById(request.companyRegistrationId());
        CompanyRegistration updatedRegistration = switch (request.status()){
            case PROCESSING -> registration.elevateStatusToProcessing(administrator);
            case APPROVED -> registration.elevateStatusToApproved(administrator);
            case REJECTED -> registration.elevateStatusToRejected(administrator);
            default -> throw new IllegalStateException("Unexpected value: " + request.status());
        };
        if (request.notes() != null) {
            updatedRegistration.addNoteForStatusChange(request.notes());
        }
        companyRegistrationRepository.add(updatedRegistration);
        registerApprovedRegistration(updatedRegistration);
        return new UpdateCompanyRegistrationStatusResponse(updatedRegistration.getCompanyName(), updatedRegistration.getStatus(), Instant.now());
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
