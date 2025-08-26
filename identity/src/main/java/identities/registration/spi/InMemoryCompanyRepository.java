package identities.registration.spi;

import identities.company.Company;
import identities.company.spi.CompanyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryCompanyRepository implements CompanyRepository {
    private final List<Company> companies = new ArrayList<>();

    @Override
    public Optional<Company> findByName(String name) {
        return companies.stream()
                .filter(company -> company.getName().equals(name))
                .findFirst();
    }

    @Override
    public Optional<Company> findByBrn(String brn) {
        return companies.stream()
                .filter(company -> company.getBrn().equals(brn))
                .findFirst();
    }

    @Override
    public void add(Company company) {
        companies.add(company);
    }
}
