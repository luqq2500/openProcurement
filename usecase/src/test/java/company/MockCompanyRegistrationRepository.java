package company;

import company.spi.CompanyRegistrationRepository;
import ddd.Stub;

import java.util.ArrayList;
import java.util.List;

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
}
