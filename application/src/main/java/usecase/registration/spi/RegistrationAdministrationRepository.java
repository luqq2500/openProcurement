package usecase.registration.spi;


import domain.registration.RegistrationAdministration;

import java.util.Optional;
import java.util.UUID;

public interface RegistrationAdministrationRepository {
    Optional<RegistrationAdministration> findLatestVersion(UUID registrationId);
    RegistrationAdministration get(UUID registrationId);
    void add(RegistrationAdministration administration);
}
