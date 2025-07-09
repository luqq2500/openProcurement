package company.application;
import company.api.CompanyRegistrator;
import company.model.CompanyStructure;
import company.spi.CompanyRepository;
import company.model.Company;

public class RegisterACompany implements CompanyRegistrator {
    private final CompanyRepository repository;
    public RegisterACompany(CompanyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Company register(String name, String structure, String brn, String tin) {
        validateCompanyUniqueness(brn, tin);
        Company company = new Company(name, structure, brn, tin);
        this.repository.add(company);
        return company;
    }

    private void validateCompanyUniqueness(String brn, String tin) {
        if (repository.findByBrn(brn).isPresent()) {
            throw new RuntimeException("Company " + brn + "is already exists");
        }
        if (repository.findByTin(tin).isPresent()) {
            throw new RuntimeException("Company " + brn + "is already exists");
        }
    }
}
