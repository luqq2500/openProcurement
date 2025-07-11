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
        Company company = new Company(
                command.name(),
                command.registrationNumber(),
                command.taxNumber(),
                command.businessStructure(),
                command.taxNumber()
        );
        this.repository.add(company);
        return company;
    }

    private void validateTaxNumber(RegisterCompanyCommand command) {
        if (repository.findByTaxNumber(command.taxNumber()).isPresent()) {
            throw new CompanyAlreadyExistException("Company " + command.taxNumber() + "is already exists");
        }

    }
    private void validateRegistrationNumber(RegisterCompanyCommand command) {
        if (repository.findByRegistrationNumber(command.registrationNumber()).isPresent()) {
            throw new CompanyAlreadyExistException("Company " + command.registrationNumber() + "is already exists");
        }
    }
}
