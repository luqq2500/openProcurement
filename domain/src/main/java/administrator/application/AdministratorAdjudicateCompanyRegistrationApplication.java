package administrator.application;

import administrator.api.CompanyRegistrationApplicationAdjudicator;
import administrator.exception.InvalidAdministratorRole;
import administrator.model.Administrator;
import company.exception.CompanyRegistrationApplicationAdjudicationException;
import company.model.CompanyRegistrationApplication;
import company.spi.CompanyRegistrationApplicationRepository;


public class AdministratorAdjudicateCompanyRegistrationApplication implements CompanyRegistrationApplicationAdjudicator {
    private static final String ADMINISTRATOR_ROLE = "Adjudicator";
    private final CompanyRegistrationApplicationRepository repository;

    public AdministratorAdjudicateCompanyRegistrationApplication(CompanyRegistrationApplicationRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompanyRegistrationApplication process(CompanyRegistrationApplication application, Administrator administrator) {
        validateAdministratorRole(administrator);
        if (!"Pending".equals(application.status())) {
            throw new CompanyRegistrationApplicationAdjudicationException(
                    "Processing of application '" + application.applicationId() + "' cannot begin: current status is '" + application.status() +
                            "' instead of required 'Pending'. Please ensure the application is newly submitted before starting adjudication."
            );        }
        return application.updateStatus("Processing", administrator.getAdministratorId());
    }

    @Override
    public CompanyRegistrationApplication approve(CompanyRegistrationApplication application, Administrator administrator) {
        validateAdministratorRole(administrator);
        if (!"Processing".equals(application.status())){
            throw new CompanyRegistrationApplicationAdjudicationException(
                    "Cannot adjudicate company registration application '" + application.applicationId() +
                            "' because its current status is '" + application.status() + "'. Only applications with 'Processing' status can be adjudicated. Please ensure the application is in the correct stage before proceeding."
            );
        }
        return application.updateStatus("Approved", administrator.getAdministratorId());
    }

    @Override
    public CompanyRegistrationApplication reject(CompanyRegistrationApplication application, Administrator administrator) {
        validateAdministratorRole(administrator);
        if (!"Processing".equals(application.status())){
            throw new CompanyRegistrationApplicationAdjudicationException(
                    "Rejection of application '" + application.applicationId() + "' is not allowed: current status is '" + application.status() +
                            "' instead of required 'Processing'. Please process the application fully before rejecting it."
            );
        }
        return application.updateStatus("Rejected", administrator.getAdministratorId());
    }

    private static void validateAdministratorRole(Administrator administrator) {
        if (!administrator.getRole().equals(ADMINISTRATOR_ROLE)) {
            throw new InvalidAdministratorRole("Only " + ADMINISTRATOR_ROLE + " roles are allowed to adjudicate company registration applications.");
        }
    }
}
