package company.spi;

import company.Company;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository {
    public void add(Company company);
    public Optional<Company> findById(UUID id);
    public Optional<Company> findByBusinessRegistrationNumber(String businessRegistrationNumber);
    public Optional<Company> findByTaxIdentificationNumber(String taxIdentificationNumber);
}
