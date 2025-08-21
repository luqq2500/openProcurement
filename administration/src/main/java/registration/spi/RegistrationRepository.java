package registration.spi;

import registration.Registration;

import java.util.UUID;

public interface RegistrationRepository {
    void add(Registration registration);
    Registration get(UUID registrationId);
}
