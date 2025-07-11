package company.application;

import company.api.CompanyRegistrationValidator;
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
        CountryRegistrationRules rule = getCountryRegistrationRules(command);
        rule.validateBusinessStructure(command.businessStructure());
        rule.validateRegistrationNumber(command.registrationNumber());
        rule.validateTaxNumber(command.taxNumber());
    }

    private CountryRegistrationRules getCountryRegistrationRules(RegisterCompanyCommand command) {
        return countryRegistrationRulesRepository.countryRegistrationRules().filter(countryRegistrationRules -> countryRegistrationRules.countryCode().equals(command.countryCode()))
                .findFirst().orElseThrow(() -> new InvalidCountryRegistrationRulesException("Country code not found."));
    }
}
