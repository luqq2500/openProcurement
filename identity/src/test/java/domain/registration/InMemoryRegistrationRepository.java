package domain.registration;

import domain.registration.spi.RegistrationRepository;

import java.util.*;

public class InMemoryRegistrationRepository implements RegistrationRepository {
    private final List<RegistrationApplication> registrations = new ArrayList<>();
    @Override
    public Optional<RegistrationApplication> findLatestByRequestId(UUID requestId) {
        return registrations.stream()
                .filter(registration -> registration.getRequestId().equals(requestId))
                .max(Comparator.comparing(RegistrationApplication::getAppliedOn));
    }
    @Override
    public Optional<RegistrationApplication> findLatestByBrn(String brn) {
        return registrations.stream()
                .filter(registration -> registration.getBrn().equals(brn))
                .max(Comparator.comparing(RegistrationApplication::getAppliedOn));
    }
    @Override
    public Optional<RegistrationApplication> findLatestByCompanyName(String name) {
        return registrations.stream()
                .filter(registration -> registration.getCompanyName().equals(name))
                .max(Comparator.comparing(RegistrationApplication::getAppliedOn));
    }
    @Override
    public void add(RegistrationApplication application) {
        registrations.add(application);
    }
}
