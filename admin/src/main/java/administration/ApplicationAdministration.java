package administration;

import java.util.UUID;

public record ApplicationAdministration(
        UUID applicationId,
        AdministrationStatus status,
        UUID administratorId,
        String message
){}
