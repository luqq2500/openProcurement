package company.spi;

import company.model.CountryRegistrationRules;

import java.util.List;

@FunctionalInterface
public interface CountryRegistrationRulesRepository {
    List<CountryRegistrationRules> countryRegistrationRules();
}
