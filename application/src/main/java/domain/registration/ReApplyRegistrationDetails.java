package domain.registration;

import java.util.UUID;

public record ReApplyRegistrationDetails(
        UUID registrationId,
        UUID accountId,
        String companyName,
        String brn,
        Structure structure,
        String firstName,
        String lastName,
        String email,
        String password
) {
}
