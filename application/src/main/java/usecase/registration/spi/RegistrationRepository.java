package usecase.registration.spi;



import domain.registration.Registration;

import java.util.Optional;
import java.util.UUID;

public interface RegistrationRepository {
    Optional<Registration> findLatest(UUID requestId);
    Optional<Registration> findLatestByBrn(String brn);
    Optional<Registration> findLatestByCompanyName(String name);
    Optional<Registration> findLatestByEmail(String email);
    void add(Registration application);
    Registration get(UUID registrationId);
}
