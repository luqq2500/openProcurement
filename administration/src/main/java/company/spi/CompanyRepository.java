package company.spi;

import company.Company;

import java.util.Optional;

public interface CompanyRepository {
    Optional<Company> findByBrn();
}
