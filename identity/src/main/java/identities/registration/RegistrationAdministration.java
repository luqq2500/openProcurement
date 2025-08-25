package identities.registration;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegistrationAdministration(
        UUID administratorId,
        RegistrationStatus statusBefore,
        RegistrationStatus newStatus,
        String notes,
        LocalDateTime administeredOn
) {
}
