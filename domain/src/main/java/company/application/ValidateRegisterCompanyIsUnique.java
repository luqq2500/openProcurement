package company.application;

import company.exception.CompanyAlreadyExistException;
import company.spi.CompanyRepository;

public class ValidateRegisterCompanyIsUnique {
    private final CompanyRepository companyRepository;


    public ValidateRegisterCompanyIsUnique(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void validateCompanyIsUnique(String registrationNumber, String taxNumber) {
        if (companyRepository.findByRegistrationNumber(registrationNumber).isPresent()) {
            throw new CompanyAlreadyExistException("Company " + registrationNumber + "is already exists");
        }
        if (companyRepository.findByTaxNumber(taxNumber).isPresent()) {
            throw new CompanyAlreadyExistException("Company " + registrationNumber + "is already exists");
        }
    }
}
