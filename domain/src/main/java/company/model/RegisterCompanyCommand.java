package company.model;

import company.exception.InvalidRegisterCompanyCommand;

public record RegisterCompanyCommand(String name, String registrationNumber, String taxNumber, String businessStructure, String countryCode) {
    public RegisterCompanyCommand{
        validateName(name);
        validateRegistrationNumber(registrationNumber);
        validateTaxNumber(taxNumber);
        validateBusinessStructure(businessStructure);
        validateCountryCode(countryCode);
    }

    public void validateName(String name) throws InvalidRegisterCompanyCommand {
        if (name == null || name.isBlank()) {
            throw new InvalidRegisterCompanyCommand("Company name cannot be empty");
        }
    }
    public void validateRegistrationNumber(String registrationNumber) throws InvalidRegisterCompanyCommand {
        if (registrationNumber == null || registrationNumber.isBlank()) {
            throw new InvalidRegisterCompanyCommand("Registration number cannot be empty");
        }
    }
    public void validateTaxNumber(String taxNumber) throws InvalidRegisterCompanyCommand {
        if (taxNumber == null || taxNumber.isBlank()) {
            throw new InvalidRegisterCompanyCommand("Tax identification number cannot be empty");
        }
    }
    public void validateBusinessStructure(String businessStructure) throws InvalidRegisterCompanyCommand {
        if (businessStructure == null || businessStructure.isBlank()) {
            throw new InvalidRegisterCompanyCommand("Business structure cannot be empty");
        }
    }
    public void validateCountryCode(String countryCode) throws InvalidRegisterCompanyCommand {
        if (countryCode == null || countryCode.isBlank()) {
            throw new InvalidRegisterCompanyCommand("Country code cannot be empty");
        }
    }


}
