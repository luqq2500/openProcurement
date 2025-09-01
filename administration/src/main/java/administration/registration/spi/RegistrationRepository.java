package administration.registration.spi;

import administration.registration.RegistrationApplication;

import java.util.UUID;

public interface RegistrationRepository {
    void add(RegistrationApplication application);
    RegistrationApplication get(UUID applicationId);
}
