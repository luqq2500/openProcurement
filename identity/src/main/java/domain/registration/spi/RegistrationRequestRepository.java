package domain.registration.spi;

import domain.registration.RegistrationApplicationRequest;

import java.util.UUID;

public interface RegistrationRequestRepository {
    RegistrationApplicationRequest getById(UUID id);
    void save(RegistrationApplicationRequest registrationApplicationRequest);
}
