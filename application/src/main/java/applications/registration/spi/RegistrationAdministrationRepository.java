package applications.registration.spi;


import applications.registration.RegistrationAdministration;

import java.util.Optional;
import java.util.UUID;

public interface RegistrationAdministrationRepository {
    Optional<RegistrationAdministration> find(UUID applicationId);
    RegistrationAdministration get(UUID applicationId);
    void add(RegistrationAdministration registration);
}
