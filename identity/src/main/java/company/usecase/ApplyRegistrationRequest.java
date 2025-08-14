package company.usecase;

import company.Structure;
import company.address.Country;

import java.util.UUID;

public record ApplyRegistrationRequest(
        UUID registrationRequestId,
        String companyName,
        String street1,
        String street2,
        String street3,
        String city,
        String zip,
        String state,
        Country country,
        String brn,
        Structure structure,
        String taxNumber
) {
}
