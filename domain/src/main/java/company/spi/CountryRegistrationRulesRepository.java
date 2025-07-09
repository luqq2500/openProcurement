package company.spi;

import company.model.CountryRegistrationRules;

import java.util.Optional;

public interface CountryRegistrationRulesRepository {
    Optional<CountryRegistrationRules> findByCountryCode(String countryCode);
}
