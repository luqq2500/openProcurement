package address.api;

import company.command.ApplyCompanyRegistrationCommand;

public interface CompanyCountryRegistrationRuleValidator {
    void validate (ApplyCompanyRegistrationCommand command);
}
