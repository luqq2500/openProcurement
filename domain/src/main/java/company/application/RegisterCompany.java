package company.application;

import company.api.CompanyRegistrator;
import company.model.Company;
import company.model.RegisterCompanyCommand;
import company.spi.CompanyRepository;

import java.util.UUID;

public class RegisterCompany implements CompanyRegistrator {
    private final CompanyRepository repository;

    public RegisterCompany(CompanyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Company register(RegisterCompanyCommand command) {
        return new Company(UUID.randomUUID().toString(), command.getCompanyName(), command.getRegistrationNumber(), command.getTaxNumber(), command.getBusinessStructure(), command.getCountryCode());
    }
}
