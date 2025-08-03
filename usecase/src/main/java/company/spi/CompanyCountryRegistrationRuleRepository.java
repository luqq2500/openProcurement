package company.spi;

import address.CountryCode;
import company.CompanyCountryRegistrationRule;

import java.util.List;
import java.util.Optional;

public interface CompanyCountryRegistrationRuleRepository {
    List<CompanyCountryRegistrationRule> rules();
    void add(CompanyCountryRegistrationRule rule);
    Optional<CompanyCountryRegistrationRule> findByCountryCode(CountryCode countryCode);
}
