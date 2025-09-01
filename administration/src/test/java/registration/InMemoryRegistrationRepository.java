package registration;

import administration.registration.RegistrationApplication;
import administration.registration.spi.RegistrationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryRegistrationRepository implements RegistrationRepository {
    private final List<RegistrationApplication> registrationApplications = new ArrayList<>();
    @Override
    public void add(RegistrationApplication application) {
        registrationApplications.add(application);
    }
    @Override
    public RegistrationApplication get(UUID applicationId) {
        return registrationApplications.stream()
                .filter(registration -> registration.applicationId()
                .equals(applicationId)).findFirst()
                .orElse(null);
    }
}
