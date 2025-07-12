package company.api;

import company.exception.InvalidCountryRegistrationRulesException;
import company.application.command.ApplyCompanyRegistrationCommand;

@FunctionalInterface
public interface CompanyRegistrationApplicationValidator {
    public void validate(ApplyCompanyRegistrationCommand command) throws InvalidCountryRegistrationRulesException;
}
