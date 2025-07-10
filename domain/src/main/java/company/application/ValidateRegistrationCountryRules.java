package company.application;

import company.api.CompanyRegistrationValidator;
import company.exception.CompanyAlreadyExistException;
import company.exception.InvalidCompanyException;
import company.exception.InvalidCountryRegistrationRulesException;
import company.model.CountryRegistrationRules;
import company.model.RegisterCompanyCommand;
import company.spi.CountryRegistrationRulesRepository;

public class ValidateRegistrationCountryRules implements CompanyRegistrationValidator {
    private final CountryRegistrationRulesRepository countryRegistrationRulesRepository;

    public ValidateRegistrationCountryRules(CountryRegistrationRulesRepository countryRegistrationRulesRepository) {
        this.countryRegistrationRulesRepository = countryRegistrationRulesRepository;
    }

    @Override
    public void validate(RegisterCompanyCommand command) throws InvalidCountryRegistrationRulesException {
        CountryRegistrationRules countryRegistrationRules = countryRegistrationRulesRepository.findByCountryCode(command.countryCode())
                .orElseThrow(() -> new InvalidCountryRegistrationRulesException("No rules found for country: " + command.countryCode()));
        countryRegistrationRules.validateBusinessStructure(command.businessStructure());
        countryRegistrationRules.validateRegistrationNumber(command.registrationNumber());
        countryRegistrationRules.validateTaxNumber(command.taxNumber());
    }
}
