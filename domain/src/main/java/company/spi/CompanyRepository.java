package company.spi;

import company.model.Company;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository {
    public void add(Company company);
    public Optional<Company> findById(UUID id);
    public Optional<Company> findByBrn(String businessRegistrationNumber);
    public Optional<Company> findByTin(String taxIdentificationNumber);
}
