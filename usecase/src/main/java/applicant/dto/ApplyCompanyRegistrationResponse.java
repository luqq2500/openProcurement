package applicant.dto;

import address.Address;
import company.CompanyStructure;

public record ApplyCompanyRegistrationResponse (
        String email,
        String companyName,
        Address address,
        String registrationNumber,
        String taxNumber,
        CompanyStructure structure
){
}
