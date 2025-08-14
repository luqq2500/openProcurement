package company.spi;

import company.CompanyAccount;

import java.util.Optional;

public interface CompanyRepository {
    Optional<CompanyAccount> findByBrn(String brn);
    void save(CompanyAccount companyAccount);
}
