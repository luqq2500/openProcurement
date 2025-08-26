package identities.company.spi;

import identities.company.Company;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository {
    Optional<Company> findByName(String name);
    Optional<Company> findByBrn(String brn);
    void add(Company company);
}
