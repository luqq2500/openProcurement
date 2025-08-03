package company;

import company.spi.CompanyRegistrationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
