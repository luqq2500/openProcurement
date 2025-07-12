package company.application;

import company.api.CompanyRegistrationApplicationValidator;
import company.exception.InvalidCountryRegistrationRulesException;
import company.application.command.ApplyCompanyRegistrationCommand;
import company.model.CountryRegistrationRules;
import company.spi.CountryRegistrationRulesRepository;

public class ValidateRegistrationApplicationCountryRules implements CompanyRegistrationApplicationValidator {
    private final CountryRegistrationRulesRepository rulesRepository;

    public ValidateRegistrationApplicationCountryRules(CountryRegistrationRulesRepository rulesRepository) {
        this.rulesRepository = rulesRepository;
    }

    @Override
    public void validate(ApplyCompanyRegistrationCommand command) throws InvalidCountryRegistrationRulesException {
        CountryRegistrationRules rule = getCountryRegistrationRules(command);
        rule.validateBusinessStructure(command.businessStructure());
        rule.validateRegistrationNumber(command.registrationNumber());
        rule.validateTaxNumber(command.taxNumber());
    }

    private CountryRegistrationRules getCountryRegistrationRules(ApplyCompanyRegistrationCommand command) {
        return rulesRepository.countryRegistrationRules().stream().filter(countryRegistrationRules -> countryRegistrationRules.countryCode().equals(command.countryCode()))
                .findFirst().orElseThrow(() -> new InvalidCountryRegistrationRulesException("Country code not found."));
    }
}
