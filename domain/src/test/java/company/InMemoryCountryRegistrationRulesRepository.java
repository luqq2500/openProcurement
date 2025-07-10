package company;

import company.model.CountryRegistrationRules;
import company.spi.CountryRegistrationRulesRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryCountryRegistrationRulesRepository implements CountryRegistrationRulesRepository {
    private final Map<String, CountryRegistrationRules> rules = new HashMap<>();
    public InMemoryCountryRegistrationRulesRepository() {
        rules.put("US", new CountryRegistrationRules("US", 9, "\\d{9}", 9, "\\d{9}",
                List.of("Sole Proprietorship", "Partnership", "Limited Liability Company (LLC)", "Corporation", "Cooperative")));
        rules.put("MY", new CountryRegistrationRules("MY", 12, "\\d{12}", 12 , "\\d{12}",
                List.of("Sole Proprietorship", "Partnership", "Limited Liability Company (LLC)", "Corporation", "Cooperative")));
    }
    @Override
    public Optional<CountryRegistrationRules> findByCountryCode(String countryCode) {
        return Optional.ofNullable(rules.get(countryCode));
    }
}
