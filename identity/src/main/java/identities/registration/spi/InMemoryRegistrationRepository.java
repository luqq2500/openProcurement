package identities.registration.spi;

import identities.registration.RegistrationApplication;

import java.util.*;

public class InMemoryRegistrationRepository implements RegistrationRepository {
    private final List<RegistrationApplication> registrations = new ArrayList<>();
    @Override
    public Optional<RegistrationApplication> find(UUID requestId) {
        return registrations.stream()
                .filter(registration -> registration.getRequestId().equals(requestId))
                .findFirst();
    }
    @Override
    public Optional<RegistrationApplication> findByBrn(String brn) {
        return registrations.stream()
                .filter(registration -> registration.getBrn().equals(brn))
                .findFirst();
    }
    @Override
    public Optional<RegistrationApplication> findByCompanyName(String name) {
        return registrations.stream()
                .filter(registration -> registration.getCompanyName().equalsIgnoreCase(name))
                .findFirst();
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
