package company;

import company.spi.CompanyRepository;
import company.model.Company;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryCompanyRepository implements CompanyRepository {
    List<Company> companies = new ArrayList<>();

    @Override
    public void add(Company company) {
        this.companies.add(company);
    }

    @Override
    public Optional<Company> findById(UUID id) {
        return companies.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Company> findByBrn(String brn) {
        return companies.stream()
                .filter(company -> company.getBrn().equals(brn))
                .findFirst();
    }

    @Override
    public Optional<Company> findByTin(String tin) {
        return companies.stream()
                .filter(company -> company.getTin().equals(tin))
                .findFirst();
    }
}
