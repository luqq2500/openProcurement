package company.spi;

import company.model.Company;

import java.util.List;

@FunctionalInterface
public interface CompanyRepository {
    List<Company> companies();
}
