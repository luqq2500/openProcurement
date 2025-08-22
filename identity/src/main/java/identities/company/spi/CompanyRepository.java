package identities.company.spi;

import identities.company.Company;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository {
    Optional<Company> findById(UUID companyId);
    Optional<Company> findByBrn(String brn);
}
