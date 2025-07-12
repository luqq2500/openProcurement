package company.api;

import company.exception.InvalidCountryRegistrationRulesException;
import company.model.ApplyCompanyRegistrationCommand;

@FunctionalInterface
public interface CompanyRegistrationApplicationValidator {
    public void validate(ApplyCompanyRegistrationCommand command) throws InvalidCountryRegistrationRulesException;
}
