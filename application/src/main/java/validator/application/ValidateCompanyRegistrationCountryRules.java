package validator.application;

import company.command.ApplyCompanyRegistrationCommand;
import validator.api.CompanyRegistrationApplicationValidator;
import company.exception.InvalidCountryRegistrationRulesException;
import company.model.CompanyRegistrationCountryRule;
import company.spi.CompanyCountryRegistrationRuleRepository;

public class ValidateCompanyRegistrationCountryRules implements CompanyRegistrationApplicationValidator {
    private final CompanyCountryRegistrationRuleRepository rulesRepository;

    public ValidateCompanyRegistrationCountryRules(CompanyCountryRegistrationRuleRepository rulesRepository) {
        this.rulesRepository = rulesRepository;
    }

    @Override
    public void validate(ApplyCompanyRegistrationCommand command) throws InvalidCountryRegistrationRulesException {
        CompanyRegistrationCountryRule rule = getCountryRegistrationRules(command);
        rule.validateBusinessStructure(command.businessStructure());
        rule.validateRegistrationNumber(command.registrationNumber());
        rule.validateTaxNumber(command.taxNumber());
    }

    private CompanyRegistrationCountryRule getCountryRegistrationRules(ApplyCompanyRegistrationCommand command) {
        return rulesRepository.countryRegistrationRules().stream().filter(companyRegistrationCountryRule -> companyRegistrationCountryRule.countryName().equals(command.address().country()))
                .findFirst().orElseThrow(() -> new InvalidCountryRegistrationRulesException("Country code not found."));
    }
}
