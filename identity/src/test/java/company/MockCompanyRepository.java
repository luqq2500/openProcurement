package company;

import company.spi.CompanyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockCompanyRepository implements CompanyRepository {
    List<CompanyAccount> companies = new ArrayList<>();
    @Override
    public Optional<CompanyAccount> findByBrn(String brn) {
        return companies.stream()
                .filter(companyAccount -> companyAccount.getBrn().equals(brn))
                .findFirst();
    }

    @Override
    public void save(CompanyAccount companyAccount) {
        companies.add(companyAccount);
    }
}
