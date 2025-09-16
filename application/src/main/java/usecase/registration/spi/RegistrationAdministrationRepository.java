package usecase.registration.spi;


import domain.registration.RegistrationAdministration;

import java.util.Optional;
import java.util.UUID;

public interface RegistrationAdministrationRepository {
    Optional<RegistrationAdministration> find(UUID applicationId);
    RegistrationAdministration get(UUID applicationId);
    void add(RegistrationAdministration registration);
}
