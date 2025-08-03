package company;

import company.spi.CompanyRepository;

import java.util.ArrayList;
import java.util.List;

public class MockCompanyRepository implements CompanyRepository {
    List<Company> companies = new ArrayList<>();
    @Override
    public List<Company> companies() {
        return companies;
    }
    @Override
    public void add(Company company) {
        companies.add(company);
    }
}
