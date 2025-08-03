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

    @Override
    public Optional<CompanyRegistration> findByCompanyRegistrationNumber(String registrationNumber) {
        return registrations.stream()
                .filter(registration -> registration.getRegistrationNumber().equals(registrationNumber))
                .findFirst();
    }

    @Override
    public Optional<CompanyRegistration> findByTaxNumber(String taxNumber) {
        return registrations.stream()
                .filter(registration -> registration.getTaxNumber().equals(taxNumber))
                .findFirst();
    }
}
