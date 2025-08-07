package applicant.dto;

import address.Address;
import company.CompanyStructure;

public record ApplyCompanyRegistrationResponse (
        String email,
        String companyName,
        String street1,
        String street2,
        String city,
        String zip,
        String state,
        String country,
        String registrationNumber,
        String taxNumber,
        CompanyStructure structure
){
}
