package company.application;

import company.api.CompanyRegistrationApplicationValidator;
import company.api.CompanyRegistrationApplier;
import company.exception.CompanyRegistrationApplicationAlreadyExist;
import company.model.ApplyCompanyRegistrationCommand;
import company.model.CompanyRegistrationApplication;
import company.spi.CompanyRegistrationApplicationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ApplyCompanyRegistration implements CompanyRegistrationApplier {
    private final CompanyRegistrationApplicationRepository repository;
    private final CompanyRegistrationApplicationValidator validator;

    public ApplyCompanyRegistration(CompanyRegistrationApplicationRepository repository, CompanyRegistrationApplicationValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public CompanyRegistrationApplication apply(ApplyCompanyRegistrationCommand command) {
        validateRegistrationApplicationIsUnique(command);
        validator.validate(command);
        return new CompanyRegistrationApplication(
                UUID.randomUUID().toString(),
                command.companyName(),
                command.registrationNumber(),
                command.taxNumber(),
                command.businessStructure(),
                command.countryCode(),
                LocalDateTime.now(),
                "PENDING",
                null,
                null
        );
    }

    private void validateRegistrationApplicationIsUnique(ApplyCompanyRegistrationCommand command) {
        if (repository.applications().stream().anyMatch(application -> application.registrationNumber().equals(command.registrationNumber()))){
            throw new CompanyRegistrationApplicationAlreadyExist("Company with registration number " + command.registrationNumber() + " already applied for registration.");
        }
        if (repository.applications().stream().anyMatch(application -> application.taxNumber().equals(command.taxNumber()))){
            throw new CompanyRegistrationApplicationAlreadyExist("Company with tax number " + command.taxNumber() + " already applied for registration.");
        }
    }
}
