package validator.api;

import address.exception.InvalidAddressException;
import validator.exception.InvalidCountryRegistrationRulesException;
import company.application.command.ApplyCompanyRegistrationCommand;

@FunctionalInterface
public interface CompanyRegistrationApplicationValidator {
    public void validate(ApplyCompanyRegistrationCommand command) throws InvalidAddressException, InvalidCountryRegistrationRulesException;
}
