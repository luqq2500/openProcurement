package company.spi;

import company.model.CompanyRegistrationCountryRule;

import java.util.List;

@FunctionalInterface
public interface CompanyCountryRegistrationRuleRepository {
    List<CompanyRegistrationCountryRule> countryRegistrationRules();
}
