package company.spi;

import company.CountryRegistrationRule;
import company.address.Country;

public interface CountryRegistrationRuleRepository {
    CountryRegistrationRule findByCountry(Country country);
}
