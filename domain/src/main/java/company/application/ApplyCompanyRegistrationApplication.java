package company.application;

import validator.api.CompanyRegistrationApplicationValidator;
import company.api.CompanyRegistrationApplier;
import company.exception.CompanyRegistrationApplicationAlreadyExist;
import company.application.command.ApplyCompanyRegistrationCommand;
import company.model.CompanyRegistrationApplication;
import company.spi.CompanyRegistrationApplicationRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class ApplyCompanyRegistrationApplication implements CompanyRegistrationApplier {
    private final CompanyRegistrationApplicationRepository repository;
    private final CompanyRegistrationApplicationValidator validator;

    public ApplyCompanyRegistrationApplication(CompanyRegistrationApplicationRepository repository, CompanyRegistrationApplicationValidator validator) {
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
                command.address(),
                LocalDateTime.now(),
                "Pending",
                null,
                null
        );
    }
    private void validateRegistrationApplicationIsUnique(ApplyCompanyRegistrationCommand command) {
        if (repository.registrations().stream().anyMatch(application -> application.registrationNumber().equals(command.registrationNumber()))){
            throw new CompanyRegistrationApplicationAlreadyExist("Company with registration number " + command.registrationNumber() + " already applied for registration.");
        }
        if (repository.registrations().stream().anyMatch(application -> application.taxNumber().equals(command.taxNumber()))){
            throw new CompanyRegistrationApplicationAlreadyExist("Company with tax number " + command.taxNumber() + " already applied for registration.");
        }
    }
}
