package domain.registration.spi;


import domain.registration.RegistrationApplication;

import java.util.Optional;
import java.util.UUID;

public interface RegistrationRepository {
    Optional<RegistrationApplication> findLatestByRequestId(UUID requestId);
    Optional<RegistrationApplication> findLatestByBrn(String registrationId);
    Optional<RegistrationApplication> findByBrn(String brn);
    Optional<RegistrationApplication> findByCompanyName(String name);
    void add(RegistrationApplication application);
    RegistrationApplication getLatest(UUID id);
}
