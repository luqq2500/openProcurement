package domain.registration;

import domain.registration.spi.RegistrationRepository;

import java.util.*;

public class InMemoryRegistrationRepository implements RegistrationRepository {
    private final List<RegistrationApplication> registrations = new ArrayList<>();
    @Override
    public Optional<RegistrationApplication> findLatestByRequestId(UUID requestId) {
        return registrations.stream()
                .filter(registration -> registration.requestId().equals(requestId))
                .sorted()
                .findFirst();
    }
    @Override
    public Optional<RegistrationApplication> findLatestByBrn(String brn) {
        return registrations.stream()
                .filter(registration -> registration.getBrn().equals(brn))
                .sorted()
                .findFirst();
    }
    @Override
    public Optional<RegistrationApplication> findLatestByCompanyName(String name) {
        return registrations.stream()
                .filter(registration -> registration.getCompanyName().equals(name))
                .sorted()
                .findFirst();
    }
    @Override
    public void add(RegistrationApplication application) {
        registrations.add(application);
    }
}
