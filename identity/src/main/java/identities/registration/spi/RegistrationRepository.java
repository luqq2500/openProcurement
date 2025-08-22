package identities.registration.spi;


import identities.registration.RegistrationApplication;

import java.util.Optional;
import java.util.UUID;

public interface RegistrationRepository {
    Optional<RegistrationApplication> findLatest(UUID requestId);
    Optional<RegistrationApplication> findLatestByBrn(String brn);
    Optional<RegistrationApplication> findLatestByCompanyName(String name);
    void add(RegistrationApplication application);
    RegistrationApplication getLatest(UUID registrationId);
}
