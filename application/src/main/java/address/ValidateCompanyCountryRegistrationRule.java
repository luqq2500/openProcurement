package address;

import address.api.CompanyCountryRegistrationRuleValidator;
import address.spi.CompanyCountryRegistrationRuleRepository;
import company.command.ApplyCompanyRegistrationCommand;
import address.exception.InvalidCountryRegistrationRulesException;
import company.CompanyRegistrationCountryRule;

public class ValidateCompanyCountryRegistrationRule implements CompanyCountryRegistrationRuleValidator {
    private final CompanyCountryRegistrationRuleRepository rulesRepository;

    public ValidateCompanyCountryRegistrationRule(CompanyCountryRegistrationRuleRepository rulesRepository) {
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
