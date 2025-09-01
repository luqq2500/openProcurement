package applications.registration;

import format.address.Address;

public record CompanyDetails(
        String companyName, Address address, String brn, Structure structure
) {
}
