package company.spi;

import company.CompanyCountryRegistrationRule;

import java.util.List;

public interface CompanyCountryRegistrationRuleRepository {
    List<CompanyCountryRegistrationRule> rules();
}
