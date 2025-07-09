package company.application;
import company.api.CompanyRegistrator;
import company.model.RegisterCompanyCommand;
import company.spi.CompanyRepository;
import company.model.Company;

public class RegisterACompany implements CompanyRegistrator {
    private final CompanyRepository repository;
    private final ValidateRegisterCompanyIsUnique validateRegisterCompanyIsUnique;
    private final ValidateCountryRegistrationRegulation validateCountryRegistrationRegulation;
    public RegisterACompany(
            CompanyRepository repository,
            ValidateRegisterCompanyIsUnique validateRegisterCompanyIsUnique,
            ValidateCountryRegistrationRegulation validateCountryRegistrationRegulation
    ) {
        this.repository = repository;
        this.validateRegisterCompanyIsUnique = validateRegisterCompanyIsUnique;
        this.validateCountryRegistrationRegulation = validateCountryRegistrationRegulation;
    }

    @Override
    public Company register(RegisterCompanyCommand command) {
        validateRegisterCompanyIsUnique.validateCompanyIsUnique(command.registrationNumber(), command.taxNumber());
        validateCountryRegistrationRegulation.validateCompanyDetails(command.countryCode(), command.registrationNumber(), command.taxNumber());
        Company company = new Company(
                command.name(),
                command.registrationNumber(),
                command.taxNumber(),
                command.structure(),
                command.taxNumber()
        );
        this.repository.add(company);
        return company;
    }
//    private void validateCompanyStructure(String structure) {
//        if (!CompanyStructure.COMPANY_STRUCTURES.contains(structure))
//            throw new InvalidCompanyException("Invalid company structure: " + structure);
//    }
}
