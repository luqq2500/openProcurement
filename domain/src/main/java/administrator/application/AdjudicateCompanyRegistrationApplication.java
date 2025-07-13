package administrator.application;

import validator.api.AdministratorRoleResponsibilityValidator;
import administrator.api.CompanyRegistrationApplicationAdjudicator;
import administrator.model.Administrator;
import company.api.CompanyRegistrator;
import administrator.exception.InvalidAdjudicationCompanyRegistrationException;
import company.model.CompanyRegistrationApplication;
import company.model.RegisterCompanyCommand;
import company.spi.CompanyRegistrationApplicationRepository;


public class AdjudicateCompanyRegistrationApplication implements CompanyRegistrationApplicationAdjudicator {
    private final CompanyRegistrationApplicationRepository repository;
    private final AdministratorRoleResponsibilityValidator validator;
    private final CompanyRegistrator companyRegistrator;

    public AdjudicateCompanyRegistrationApplication(CompanyRegistrationApplicationRepository repository, AdministratorRoleResponsibilityValidator validator, CompanyRegistrator companyRegistrator) {
        this.repository = repository;
        this.validator = validator;
        this.companyRegistrator = companyRegistrator;
    }

    @Override
    public CompanyRegistrationApplication process(CompanyRegistrationApplication application, Administrator administrator) {
        validator.validate(administrator, "processCompanyRegistrationApplication");
        if (!"Pending".equals(application.status())) {
            throw new InvalidAdjudicationCompanyRegistrationException(
                    "Processing of application '" + application.applicationId() + "' cannot begin: current status is '" + application.status() +
                            "' instead of required 'Pending'. Please ensure the application is newly submitted before starting adjudication."
            );        }
        return application.updateStatus("Processing", administrator.getAdministratorId());
    }

    @Override
    public CompanyRegistrationApplication approve(CompanyRegistrationApplication application, Administrator administrator) {
        validator.validate(administrator, "approveCompanyRegistrationApplication");
        if (!"Processing".equals(application.status())){
            throw new InvalidAdjudicationCompanyRegistrationException(
                    "Cannot adjudicate company registration application '" + application.applicationId() +
                            "' because its current status is '" + application.status() + "'. Only applications with 'Processing' status can be adjudicated. Please ensure the application is in the correct stage before proceeding."
            );
        }
        companyRegistrator.register(new RegisterCompanyCommand(application));
        return application.updateStatus("Approved", administrator.getAdministratorId());
    }

    @Override
    public CompanyRegistrationApplication reject(CompanyRegistrationApplication application, Administrator administrator) {
        validator.validate(administrator, "rejectCompanyRegistrationApplication");
        if (!"Processing".equals(application.status())){
            throw new InvalidAdjudicationCompanyRegistrationException(
                    "Rejection of application '" + application.applicationId() + "' is not allowed: current status is '" + application.status() +
                            "' instead of required 'Processing'. Please process the application fully before rejecting it."
            );
        }
        return application.updateStatus("Rejected", administrator.getAdministratorId());
    }
}
