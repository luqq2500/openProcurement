package company.spi;

import company.RegistrationApplication;

import java.util.Optional;
import java.util.UUID;

public interface RegistrationApplicationRepository {
    Optional<RegistrationApplication> getByBrn(String brn);
    void save(RegistrationApplication application);
}
