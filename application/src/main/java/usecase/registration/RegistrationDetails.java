package usecase.registration;

import domain.registration.Structure;

import java.util.UUID;

public record RegistrationDetails(
        UUID requestId,
        String companyName,
        String brn,
        Structure structure,
        String firstName,
        String lastName,
        String email,
        String password
) {
}
