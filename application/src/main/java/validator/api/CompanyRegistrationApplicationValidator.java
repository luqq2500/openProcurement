package validator.api;

import address.exception.InvalidAddressException;
import company.command.ApplyCompanyRegistrationCommand;
import company.exception.InvalidCountryRegistrationRulesException;

@FunctionalInterface
public interface CompanyRegistrationApplicationValidator {
    public void validate(ApplyCompanyRegistrationCommand command) throws InvalidAddressException, InvalidCountryRegistrationRulesException;
}
