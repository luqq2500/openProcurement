package usecase.registration.spi.mock;

import annotation.Stub;
import domain.registration.Registration;
import usecase.registration.spi.RegistrationRepository;

import java.util.*;

@Stub
public class InMemoryRegistrationRepository implements RegistrationRepository {
    private final List<Registration> registrations = new ArrayList<>();
    @Override
    public Optional<Registration> findLatestVersion(UUID registrationId) {
        return registrations.stream()
                .filter(registration -> registration.registrationId().equals(registrationId))
                .max(Comparator.comparing(Registration::version));
    }
    @Override
    public void add(Registration application) {
        registrations.add(application);
    }
    @Override
    public Registration get(UUID registrationId) {
        return registrations.stream()
                .filter(registration -> registration.registrationId().equals(registrationId))
                .findFirst()
                .orElse(null);
    }
    @Override
    public Optional<Registration> findLatestByBrn(String brn) {
        return registrations.stream()
                .filter(registration -> registration.getBrn().equalsIgnoreCase(brn))
                .max(Comparator.comparing(Registration::version));
    }
    @Override
    public Optional<Registration> findLatestByCompanyName(String name) {
        return registrations.stream()
                .filter(registration -> registration.getCompanyName().equalsIgnoreCase(name))
                .max(Comparator.comparing(Registration::version));
    }

    @Override
    public Optional<Registration> findLatestByEmail(String email) {
        return registrations.stream()
                .filter(registration -> registration.getEmail().equalsIgnoreCase(email))
                .max(Comparator.comparing(Registration::version));
    }
}
