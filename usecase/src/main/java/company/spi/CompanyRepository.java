package company.spi;

import company.Company;

import java.util.List;

public interface CompanyRepository {
    List<Company> companies();
    void add(Company company);
    void addListOf(List<Company> companies);
}
