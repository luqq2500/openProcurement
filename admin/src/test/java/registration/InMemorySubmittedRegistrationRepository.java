package registration;

import identity.registration.SubmittedRegistration;
import identity.registration.spi.SubmittedRegistrationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemorySubmittedRegistrationRepository implements SubmittedRegistrationRepository {
    private final List<SubmittedRegistration> submittedRegistrations = new ArrayList<>();
    @Override
    public void add(SubmittedRegistration submittedRegistration) {
        submittedRegistrations.add(submittedRegistration);
    }
    @Override
    public SubmittedRegistration get(UUID registrationId) {
        return submittedRegistrations.stream()
                .filter(registration -> registration.requestId()
                .equals(registrationId)).findFirst()
                .orElse(null);
    }
}
