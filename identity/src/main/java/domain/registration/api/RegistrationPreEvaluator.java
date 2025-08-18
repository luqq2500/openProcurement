package domain.registration.api;

import domain.registration.events.domain.RegistrationApplicationSubmitted;

public interface RegistrationPreEvaluator {
    void evaluate(RegistrationApplicationSubmitted submittedRegistration);
}
