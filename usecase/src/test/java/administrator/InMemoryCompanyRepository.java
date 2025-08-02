package administrator;

import company.Company;
import company.spi.CompanyRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCompanyRepository implements CompanyRepository {
    private List<Company> companies = new ArrayList<>();
    @Override
    public List<Company> companies() {
        return companies;
    }
    @Override
    public void add(Company company) {
        companies.add(company);
    }
    @Override
    public void addListOf(List<Company> companies) {
        this.companies.addAll(companies);
    }
}
