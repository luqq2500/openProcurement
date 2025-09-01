package applications.registration.spi;

import applications.registration.RegistrationRequest;

import java.util.UUID;

public interface RegistrationRequestRepository {
    RegistrationRequest getById(UUID id);
    void add(RegistrationRequest registrationRequest);
}
