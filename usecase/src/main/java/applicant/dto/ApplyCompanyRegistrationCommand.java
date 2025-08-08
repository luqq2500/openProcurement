package applicant.dto;

import address.Address;
import company.CompanyStructure;

import java.util.UUID;

public record ApplyCompanyRegistrationCommand(
        UUID requestId,
        String companyName,
        Address address,
        String registrationNumber,
        String taxNumber,
        CompanyStructure structure
) {
}
