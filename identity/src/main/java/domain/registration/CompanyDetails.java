package domain.registration;

import domain.address.Address;
import domain.company.Structure;

public record CompanyDetails(
        String companyName, Address address, String brn, Structure structure
) {
}
