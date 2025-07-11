package company.application;
import company.api.CompanyRegistrationValidator;
import company.api.CompanyRegistrator;
import company.exception.CompanyAlreadyExistException;
import company.model.RegisterCompanyCommand;
import company.spi.CompanyRepository;
import company.model.Company;

public class RegisterACompany implements CompanyRegistrator {
    private final CompanyRepository repository;
    private final CompanyRegistrationValidator countryRegistrationRuleValidator;
    public RegisterACompany(CompanyRepository repository, CompanyRegistrationValidator countryRegistrationRuleValidator) {
        this.repository = repository;
        this.countryRegistrationRuleValidator = countryRegistrationRuleValidator;
    }
    @Override
    public Company register(RegisterCompanyCommand command) {
        validateRegistrationNumber(command);
        validateTaxNumber(command);
        countryRegistrationRuleValidator.validate(command);
        return new Company(
                command.name(),
                command.registrationNumber(),
                command.taxNumber(),
                command.businessStructure(),
                command.taxNumber()
        );
    }

    private void validateRegistrationNumber(RegisterCompanyCommand command) {
        if (repository.companies().anyMatch(company -> company.getRegistrationNumber().equals(command.registrationNumber()))) {
            throw new CompanyAlreadyExistException("Company with registration number " + command.registrationNumber() + " already exists");
        }

    }

    private void validateTaxNumber(RegisterCompanyCommand command) {
        if (repository.companies().anyMatch(company -> company.getTaxNumber().equals(command.taxNumber()))) {
            throw new CompanyAlreadyExistException("Company with tax number " + command.taxNumber() + " already exists");
        }
    }
}
