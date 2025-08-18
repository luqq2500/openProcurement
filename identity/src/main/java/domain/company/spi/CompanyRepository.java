package domain.company.spi;

import domain.account.company.Company;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository {
    Optional<Company> findById(UUID companyId);
    Optional<Company> findByBrn(String brn);
}
