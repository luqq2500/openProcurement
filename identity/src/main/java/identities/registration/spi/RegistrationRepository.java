package identities.registration.spi;


import identities.registration.RegistrationApplication;

import java.util.Optional;
import java.util.UUID;

public interface RegistrationRepository {
    Optional<RegistrationApplication> find(UUID requestId);
    Optional<RegistrationApplication> findByBrn(String brn);
    Optional<RegistrationApplication> findByCompanyName(String name);
    void add(RegistrationApplication application);
    RegistrationApplication get(UUID registrationId);
}
