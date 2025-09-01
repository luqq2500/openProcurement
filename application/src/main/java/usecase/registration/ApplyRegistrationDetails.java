package usecase.registration;

import applications.address.Address;
import applications.registration.Structure;

import java.util.UUID;

public record ApplyRegistrationDetails(
        UUID requestId,
        String companyName,
        Address address,
        String brn,
        Structure structure,
        String firstName,
        String lastName,
        String email,
        String password
) {
}
