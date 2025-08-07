package company;

import company.exception.CompanyRegistrationNotFound;
import company.exception.CompanyRegistrationRequestNotFound;
import company.spi.CompanyRegistrationRepository;
import ddd.Stub;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Stub
public class MockCompanyRegistrationRepository implements CompanyRegistrationRepository {
    List<CompanyRegistration> registrations = new ArrayList<>();
    @Override
    public List<CompanyRegistration> registrations() {
        return registrations;
    }
    @Override
    public void add(CompanyRegistration registration) {
        registrations.add(registration);
    }

    @Override
    public CompanyRegistration getById(UUID id) {
        return registrations.stream()
                .filter(registration -> registration.getId().equals(id))
                .findFirst().orElseThrow(()-> new CompanyRegistrationRequestNotFound("Company registration does not exist."));
    }
}
