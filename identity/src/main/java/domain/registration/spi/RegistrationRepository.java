package domain.registration.spi;


import domain.registration.RegistrationApplication;

import java.util.Optional;
import java.util.UUID;

public interface RegistrationRepository {
    Optional<RegistrationApplication> findLatestByRequestId(UUID requestId);
    Optional<RegistrationApplication> findLatestByBrn(String brn);
    Optional<RegistrationApplication> findLatestByCompanyName(String name);
    void add(RegistrationApplication application);
}
