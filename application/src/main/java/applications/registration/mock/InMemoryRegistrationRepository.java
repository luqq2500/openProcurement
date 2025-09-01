package applications.registration.mock;

import annotation.Stub;
import applications.registration.Registration;
import applications.registration.spi.RegistrationRepository;

import java.util.*;

@Stub
public class InMemoryRegistrationRepository implements RegistrationRepository {
    private final List<Registration> registrations = new ArrayList<>();
    @Override
    public Optional<Registration> findLatest(UUID requestId) {
        return registrations.stream()
                .filter(registration -> registration.getRequestId().equals(requestId))
                .max(Comparator.comparing(Registration::appliedOn));
    }
    @Override
    public Optional<Registration> findLatestByBrn(String brn) {
        return registrations.stream()
                .filter(registration -> registration.getBrn().equals(brn))
                .max(Comparator.comparing(Registration::appliedOn));
    }
    @Override
    public Optional<Registration> findLatestByCompanyName(String name) {
        return registrations.stream()
                .filter(registration -> registration.getCompanyName().equalsIgnoreCase(name))
                .max(Comparator.comparing(Registration::appliedOn));
    }

    @Override
    public Optional<Registration> findLatestByEmail(String email) {
        return registrations.stream()
                .filter(registration -> registration.getEmail().equalsIgnoreCase(email))
                .max(Comparator.comparing(Registration::appliedOn));
    }

    @Override
    public void add(Registration application) {
        registrations.add(application);
    }
    @Override
    public Registration get(UUID requestId) {
        return registrations.stream()
                .filter(registration -> registration.getRequestId().equals(requestId))
                .findFirst()
                .orElse(null);
    }
}
