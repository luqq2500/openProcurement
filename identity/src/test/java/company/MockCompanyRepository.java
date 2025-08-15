package company;

import company.spi.CompanyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockCompanyRepository implements CompanyRepository {
    List<Company> companies = new ArrayList<>();
    @Override
    public Optional<Company> findByBrn(String brn) {
        return companies.stream()
                .filter(company -> company.getBrn().equals(brn))
                .findFirst();
    }

    @Override
    public void save(Company company) {
        companies.add(company);
    }
}
