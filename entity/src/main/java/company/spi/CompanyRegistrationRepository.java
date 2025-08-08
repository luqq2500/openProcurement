package company.spi;

import company.CompanyRegistration;

import java.util.List;
import java.util.UUID;

public interface CompanyRegistrationRepository {
    List<CompanyRegistration> registrations();
    void add(CompanyRegistration registration);
    CompanyRegistration getById(UUID uuid);
}
