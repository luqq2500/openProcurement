package company.spi;

import company.RegistrationRequest;

import java.util.UUID;

public interface RegistrationRequestRepository {
    RegistrationRequest getById(UUID id);
    void save(RegistrationRequest registrationRequest);
}
