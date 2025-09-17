package usecase.registration.dto;

import domain.registration.Structure;

import java.util.UUID;

public record RegistrationDetails(
        UUID accountId,
        UUID registrationId,
        String companyName,
        String brn,
        Structure structure,
        String firstName,
        String lastName,
        String email,
        String password
) {
}
