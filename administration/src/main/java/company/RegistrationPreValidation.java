package company;

import java.util.UUID;

public record RegistrationPreValidation(
        UUID registrationId, String brn, String taxNumber, String username) {
}
