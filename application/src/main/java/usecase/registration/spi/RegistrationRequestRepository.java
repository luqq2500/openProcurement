package usecase.registration.spi;

import domain.registration.RegistrationRequest;

import java.util.UUID;

public interface RegistrationRequestRepository {
    RegistrationRequest get(UUID id);
    void add(RegistrationRequest registrationRequest);
}
