package domain.registration.api;

import java.util.UUID;

public interface RegistrationPreEvaluator {
    void evaluate(UUID registrationId);
}
