package company.spi;

import company.registration.RegistrationApplication;

import java.util.Optional;

public interface RegistrationApplicationRepository {
    Optional<RegistrationApplication> findByBrn(String brn);
    void delete(RegistrationApplication application);
}
