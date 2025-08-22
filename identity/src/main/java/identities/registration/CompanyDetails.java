package identities.registration;

import identities.address.Address;
import identities.company.Structure;

public record CompanyDetails(
        String companyName, Address address, String brn, Structure structure
) {
}
