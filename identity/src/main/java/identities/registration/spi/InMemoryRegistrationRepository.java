package identities.registration.spi;

import identities.registration.RegistrationApplication;

import java.util.*;

public class InMemoryRegistrationRepository implements RegistrationRepository {
    private final List<RegistrationApplication> registrations = new ArrayList<>();
    @Override
    public Optional<RegistrationApplication> findLatest(UUID requestId) {
        return registrations.stream()
                .filter(registration -> registration.getRequestId().equals(requestId))
                .max(Comparator.comparing(RegistrationApplication::appliedOn));
    }
    @Override
    public Optional<RegistrationApplication> findLatestByBrn(String brn) {
        return registrations.stream()
                .filter(registration -> registration.getBrn().equals(brn))
                .max(Comparator.comparing(RegistrationApplication::appliedOn));
    }
    @Override
    public Optional<RegistrationApplication> findLatestByCompanyName(String name) {
        return registrations.stream()
                .filter(registration -> registration.getCompanyName().equalsIgnoreCase(name))
                .max(Comparator.comparing(RegistrationApplication::appliedOn));
    }

    @Override
    public Optional<RegistrationApplication> findLatestByEmail(String email) {
        return registrations.stream()
                .filter(registration -> registration.getEmail().equalsIgnoreCase(email))
                .max(Comparator.comparing(RegistrationApplication::appliedOn));
    }

    @Override
    public void add(RegistrationApplication application) {
        registrations.add(application);
    }
    @Override
    public RegistrationApplication get(UUID requestId) {
        return registrations.stream()
                .filter(registration -> registration.getRequestId().equals(requestId))
                .findFirst()
                .orElse(null);
    }
}
