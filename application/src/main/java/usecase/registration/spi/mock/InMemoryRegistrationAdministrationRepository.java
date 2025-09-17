package usecase.registration.spi.mock;

import annotation.Stub;
import domain.registration.RegistrationAdministration;
import usecase.registration.spi.RegistrationAdministrationRepository;

import java.util.*;

@Stub
public class InMemoryRegistrationAdministrationRepository implements RegistrationAdministrationRepository {
    private final List<RegistrationAdministration> administrations = new ArrayList<>();
    @Override
    public Optional<RegistrationAdministration> findLatestVersion(UUID registrationId) {
        return administrations.stream()
                .filter(administration -> administration.registrationId().equals(registrationId))
                .max(Comparator.comparing(RegistrationAdministration::version));
    }
    @Override
    public RegistrationAdministration get(UUID registrationId) {
        return administrations.stream()
                .filter(administration -> administration.registrationId().equals(registrationId))
                .findFirst()
                .orElse(null);
    }
    @Override
    public void add(RegistrationAdministration administration) {
        administrations.add(administration);
    }
}
