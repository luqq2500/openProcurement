package company.application;

import company.api.CompanyRegistrator;
import company.model.Company;
import company.spi.CompanyRepository;

import java.util.UUID;

public class RegisterCompany implements CompanyRegistrator {
    private final CompanyRepository repository;

    public RegisterCompany(CompanyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Company register(String companyName, String registrationNumber, String taxNumber, String businessStructure, String countryCode) {
        return new Company(UUID.randomUUID().toString(), companyName, registrationNumber, taxNumber, businessStructure, countryCode);
    }
}
