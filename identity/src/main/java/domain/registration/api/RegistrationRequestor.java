package domain.registration.api;

import java.util.UUID;

public interface RegistrationRequestor {
    void request(UUID guessAccountId);
}
