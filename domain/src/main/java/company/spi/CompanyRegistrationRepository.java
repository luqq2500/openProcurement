package company.spi;

import company.CompanyRegistration;

import java.util.List;

public interface CompanyRegistrationRepository {
    List<CompanyRegistration> registrations();
}
