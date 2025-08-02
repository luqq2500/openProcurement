package administrator;

import company.CompanyRegistration;
import company.spi.CompanyRegistrationRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCompanyRegistrationRepository implements CompanyRegistrationRepository {
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
    public void addListOf(List<CompanyRegistration> registrations) {
        this.registrations.addAll(registrations);
    }
}
