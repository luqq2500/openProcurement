package company.application;


import company.api.CompanyRegistrator;
import company.model.Company;
import company.model.CompanyRegistrationApplication;
import company.spi.CompanyRepository;

import java.util.UUID;

public class RegisterCompany implements CompanyRegistrator {
    private final CompanyRepository repository;

    public RegisterCompany(CompanyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Company register(CompanyRegistrationApplication command) {
        return new Company(UUID.randomUUID().toString(), command.companyName(), command.registrationNumber(), command.taxNumber(), command.businessStructure(), command.address());
    }
}
