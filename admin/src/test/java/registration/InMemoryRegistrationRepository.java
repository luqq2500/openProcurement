package registration;

import identity.registration.Registration;
import identity.registration.spi.RegistrationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryRegistrationRepository implements RegistrationRepository {
    private final List<Registration> registrations = new ArrayList<>();
    @Override
    public void add(Registration registration) {
        registrations.add(registration);
    }
    @Override
    public Registration get(UUID registrationId) {
        return registrations.stream()
                .filter(registration -> registration.id()
                .equals(registrationId)).findFirst()
                .orElse(null);
    }
}
