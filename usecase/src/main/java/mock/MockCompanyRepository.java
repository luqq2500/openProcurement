package mock;

import company.Company;
import company.spi.CompanyRepository;
import ddd.Stub;

import java.util.ArrayList;
import java.util.List;

@Stub
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
