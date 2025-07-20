package address.spi;

import company.CompanyRegistrationCountryRule;

import java.util.List;

@FunctionalInterface
public interface CompanyCountryRegistrationRuleRepository {
    List<CompanyRegistrationCountryRule> countryRegistrationRules();
}
