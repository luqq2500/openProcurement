package identity.registration.spi;

import identity.registration.SubmittedRegistration;

import java.util.UUID;

public interface SubmittedRegistrationRepository {
    void add(SubmittedRegistration submittedRegistration);
    SubmittedRegistration get(UUID registrationId);
}
