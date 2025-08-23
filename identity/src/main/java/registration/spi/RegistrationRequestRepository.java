package registration.spi;

import registration.RegistrationRequest;

import java.util.UUID;

public interface RegistrationRequestRepository {
    RegistrationRequest getById(UUID id);
    void add(RegistrationRequest registrationRequest);
}
