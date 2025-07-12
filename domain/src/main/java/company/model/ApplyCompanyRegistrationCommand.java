package company.model;

public record ApplyCompanyRegistrationCommand (
        String companyName,
        String registrationNumber,
        String taxNumber,
        String businessStructure,
        String countryCode
){
}
