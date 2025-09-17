package usecase.registration.api;

import java.util.UUID;

public interface RegistrationRequester {
    void request(UUID accountId);
}
