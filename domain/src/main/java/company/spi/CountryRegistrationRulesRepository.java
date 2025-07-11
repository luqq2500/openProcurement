package company.spi;

import company.model.CountryRegistrationRules;

import java.util.stream.Stream;

@FunctionalInterface
public interface CountryRegistrationRulesRepository {
    Stream<CountryRegistrationRules> countryRegistrationRules();
}
