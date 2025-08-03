package company;

import address.CountryCode;
import company.spi.CompanyCountryRegistrationRuleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockCompanyCountryRegistrationRuleRepository implements CompanyCountryRegistrationRuleRepository {
    List<CompanyCountryRegistrationRule> rules = new ArrayList<>();
    @Override
    public List<CompanyCountryRegistrationRule> rules() {
        return rules;
    }

    @Override
    public void add(CompanyCountryRegistrationRule rule) {
        rules.add(rule);
    }

    @Override
    public Optional<CompanyCountryRegistrationRule> findByCountryCode(CountryCode countryCode) {
        return rules.stream()
                .filter(rule -> rule.countryCode().equals(countryCode))
                .findFirst();
    }
}
