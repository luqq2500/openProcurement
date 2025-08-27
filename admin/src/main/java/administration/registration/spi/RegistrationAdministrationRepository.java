package administration.registration.spi;

import administration.registration.RegistrationAdministration;

import java.util.Optional;
import java.util.UUID;

public interface RegistrationAdministrationRepository {
    RegistrationAdministration get(UUID applicationId);
    Optional<RegistrationAdministration> find(UUID applicationId);
    void add(RegistrationAdministration registration);
}
