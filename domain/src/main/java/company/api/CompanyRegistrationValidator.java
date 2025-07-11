package company.api;

import company.exception.CompanyAlreadyExistException;
import company.exception.InvalidCountryRegistrationRulesException;
import company.model.RegisterCompanyCommand;

public interface CompanyRegistrationValidator {
    public void validate(RegisterCompanyCommand command) throws CompanyAlreadyExistException, InvalidCountryRegistrationRulesException;
}
