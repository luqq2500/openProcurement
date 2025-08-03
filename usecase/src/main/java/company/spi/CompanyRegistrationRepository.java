package company.spi;

import company.CompanyRegistration;

import java.util.List;
import java.util.Optional;

public interface CompanyRegistrationRepository {
    List<CompanyRegistration> registrations();
    void add(CompanyRegistration registration);
    Optional<CompanyRegistration> findByCompanyRegistrationNumber(String registrationNumber);
    Optional<CompanyRegistration> findByTaxNumber(String taxNumber);
}
