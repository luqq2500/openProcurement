package applications.registration;

import applications.address.Address;

public record CompanyDetails(
        String companyName, Address address, String brn, Structure structure
) {
}
