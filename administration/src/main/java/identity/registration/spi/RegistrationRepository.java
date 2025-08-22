package identity.registration.spi;

import identity.registration.Registration;

import java.util.UUID;

public interface RegistrationRepository {
    void add(Registration registration);
    Registration get(UUID registrationId);
}
