package administration.registration;

import administration.AdministrationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegistrationAdministration(
        UUID applicationId,
        AdministrationStatus status,
        UUID administratorId,
        String notes,
        LocalDateTime administeredOn
){}
