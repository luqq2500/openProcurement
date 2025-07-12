package company.application.command;

import company.exception.InvalidCompanyRegistrationApplicationCommand;

public record ApplyCompanyRegistrationCommand (
        String companyName,
        String registrationNumber,
        String taxNumber,
        String businessStructure,
        String countryCode
){
    public ApplyCompanyRegistrationCommand {
        if (companyName.isBlank()){
            throw new InvalidCompanyRegistrationApplicationCommand("Company name cannot be blank");
        }
        if (registrationNumber.isBlank()){
            throw new InvalidCompanyRegistrationApplicationCommand("Registration number cannot be blank");
        }
        if (taxNumber.isBlank()){
            throw new InvalidCompanyRegistrationApplicationCommand("Tax number cannot be blank");
        }
        if (businessStructure.isBlank()){
            throw new InvalidCompanyRegistrationApplicationCommand("Business structure cannot be blank");
        }
        if (countryCode.isBlank()){
            throw new InvalidCompanyRegistrationApplicationCommand("Country code cannot be blank");
        }
    }
}
