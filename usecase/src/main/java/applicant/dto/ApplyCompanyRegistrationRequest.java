package applicant.dto;

import address.Address;
import company.CompanyStructure;

import java.util.UUID;

public record ApplyCompanyRegistrationRequest(
        UUID requestId,
        String email,
        String companyName,
        Address address,
        String registrationNumber,
        String taxNumber,
        CompanyStructure structure
) {
}
