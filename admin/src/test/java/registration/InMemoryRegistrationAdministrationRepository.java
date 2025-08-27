package registration;

import administration.registration.RegistrationAdministration;
import administration.registration.spi.RegistrationAdministrationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryRegistrationAdministrationRepository implements RegistrationAdministrationRepository {
    private final List<RegistrationAdministration> administrations = new ArrayList<>();
    @Override
    public RegistrationAdministration get(UUID applicationId) {
        return administrations.stream()
                .filter(administration -> administration.applicationId().equals(applicationId))
                .findFirst()
                .orElse(null);
    }
    @Override
    public Optional<RegistrationAdministration> find(UUID applicationId) {
        return administrations.stream()
                .filter(administration -> administration.applicationId().equals(applicationId))
                .findFirst();
    }
    @Override
    public void add(RegistrationAdministration registration) {
        administrations.add(registration);
    }
}
