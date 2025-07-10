package company.application;

import company.api.CompanyRegistrationValidator;
import company.exception.CompanyAlreadyExistException;
import company.exception.InvalidCompanyException;
import company.exception.InvalidCountryRegistrationRulesException;
import company.model.RegisterCompanyCommand;

public class ValidateRegisterCompanyCommandFormat implements CompanyRegistrationValidator {
    @Override
    public void validate(RegisterCompanyCommand command) throws InvalidCompanyException, CompanyAlreadyExistException, InvalidCountryRegistrationRulesException {
        if (command.name() == null || command.name().isBlank()) {
            throw new InvalidCompanyException("Company name cannot be empty");
        }
        if (command.structure() == null || command.structure().isBlank()) {
            throw new InvalidCompanyException("Company structure cannot be empty");
        }
        if (command.registrationNumber() == null || command.registrationNumber().isBlank()) {
            throw new InvalidCompanyException("Business registration number cannot be empty");
        }
        if (command.taxNumber() == null || command.taxNumber().isBlank()) {
            throw new InvalidCompanyException("Tax identification number cannot be empty");
        }
        if (command.countryCode() == null || command.countryCode().isBlank()) {
            throw new InvalidCompanyException("Country code cannot be empty");
        }
    }
}
