package mock;

import address.Country;
import company.CompanyCountryRegistrationRule;
import company.spi.CompanyCountryRegistrationRuleRepository;
import ddd.Stub;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stub
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
    public Optional<CompanyCountryRegistrationRule> findByCountryCode(Country country) {
        return rules.stream()
                .filter(rule -> rule.country().equals(country))
                .findFirst();
    }
}
