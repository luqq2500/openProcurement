package company.spi;

import company.CompanyRegistration;

import java.util.List;

public interface CompanyRegistrationRepository {
    List<CompanyRegistration> registrations();
    void add(CompanyRegistration registration);
    void addListOf(List<CompanyRegistration> registrations);
}
