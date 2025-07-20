package company.spi;

import company.Company;

import java.util.List;

@FunctionalInterface
public interface CompanyRepository {
    List<Company> companies();
}
