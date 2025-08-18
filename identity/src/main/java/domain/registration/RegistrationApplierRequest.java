package domain.registration;

import domain.address.Address;
import domain.company.Structure;

import java.util.UUID;

public record RegistrationApplierRequest(
        UUID requestId,
        String companyName,
        Address address,
        String brn,
        Structure structure,
        String firstName,
        String lastName,
        String username,
        String password
) {
}
