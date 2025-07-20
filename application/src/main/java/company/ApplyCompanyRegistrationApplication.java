package company;

import address.api.AddressValidator;
import address.api.CompanyCountryRegistrationRuleValidator;
import company.command.ApplyCompanyRegistrationCommand;
import company.api.CompanyRegistrationApplier;
import company.exception.CompanyRegistrationApplicationAlreadyExist;
import company.spi.CompanyRegistrationApplicationRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class ApplyCompanyRegistrationApplication implements CompanyRegistrationApplier {
    private final CompanyRegistrationApplicationRepository repository;
    private final AddressValidator addressValidator;
    private final CompanyCountryRegistrationRuleValidator countryRuleValidator;

    public ApplyCompanyRegistrationApplication(CompanyRegistrationApplicationRepository repository, AddressValidator addressValidator, CompanyCountryRegistrationRuleValidator countryRuleValidator) {
        this.repository = repository;
        this.addressValidator = addressValidator;
        this.countryRuleValidator = countryRuleValidator;
    }
    @Override
    public CompanyRegistrationApplication apply(ApplyCompanyRegistrationCommand command) {
        addressValidator.validate(command.address());
        countryRuleValidator.validate(command);
        validateRegistrationApplicationIsUnique(command);
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
            throw new CompanyRegistrationApplicationAlreadyExist("Company with registration number " + command.registrationNumber() + " has already applied for registration.");
        }
        if (repository.registrations().stream().anyMatch(application -> application.taxNumber().equals(command.taxNumber()))){
            throw new CompanyRegistrationApplicationAlreadyExist("Company with tax number " + command.taxNumber() + " has already applied for registration.");
        }
    }
}
