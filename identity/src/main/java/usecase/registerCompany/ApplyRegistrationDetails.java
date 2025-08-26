package usecase.registerCompany;

import identities.address.Address;
import identities.company.Structure;

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
