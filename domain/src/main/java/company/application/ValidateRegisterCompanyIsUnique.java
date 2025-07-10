package company.application;

import company.api.CompanyRegistrationValidator;
import company.exception.CompanyAlreadyExistException;
import company.exception.InvalidCompanyException;
import company.exception.InvalidCountryRegistrationRulesException;
import company.model.RegisterCompanyCommand;
import company.spi.CompanyRepository;

public class ValidateRegisterCompanyIsUnique implements CompanyRegistrationValidator {
    private final CompanyRepository companyRepository;

    public ValidateRegisterCompanyIsUnique(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public void validate(RegisterCompanyCommand command) throws CompanyAlreadyExistException {
        if (companyRepository.findByRegistrationNumber(command.registrationNumber()).isPresent()) {
            throw new CompanyAlreadyExistException("Company " + command.registrationNumber() + "is already exists");
        }
        if (companyRepository.findByTaxNumber(command.taxNumber()).isPresent()) {
            throw new CompanyAlreadyExistException("Company " + command.taxNumber() + "is already exists");
        }
    }
}
