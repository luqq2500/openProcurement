package company.application;
import company.api.CompanyRegistrationValidator;
import company.api.CompanyRegistrator;
import company.exception.InvalidCompanyException;
import company.model.RegisterCompanyCommand;
import company.spi.CompanyRepository;
import company.model.Company;

import java.util.List;

public class RegisterACompany implements CompanyRegistrator {
    private final CompanyRepository repository;
    private final List<CompanyRegistrationValidator> validators;
    public RegisterACompany(CompanyRepository repository, List<CompanyRegistrationValidator> validators) {
        this.repository = repository;
        this.validators = validators;
    }

    @Override
    public Company register(RegisterCompanyCommand command) {
        validateWithValidators(command);
        Company company = new Company(
                command.name(),
                command.registrationNumber(),
                command.taxNumber(),
                command.structure(),
                command.taxNumber()
        );
        this.repository.add(company);
        return company;
    }

    private void validateWithValidators(RegisterCompanyCommand command) {
        for (CompanyRegistrationValidator validator : validators) {
            validator.validate(command);
        }
    }

}
