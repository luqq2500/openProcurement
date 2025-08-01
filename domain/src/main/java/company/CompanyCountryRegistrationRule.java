package company;

import address.CountryCode;

import java.util.List;

public record CompanyCountryRegistrationRule (
        CountryCode countryCode,
        int registrationNumberLength,
        String registrationNumberPattern,
        int taxNumberLength,
        String taxNumberPattern,
        List<CompanyStructure> companyStructures){
    public void validateRegistrationNumber(String registrationNumber) {
        if (registrationNumber==null || registrationNumber.length()!=registrationNumberLength || !registrationNumber.matches(registrationNumberPattern)){
            throw new RuntimeException(
                    "Registration number for country " + countryCode +
                            " must be " + registrationNumberLength + " characters long, " +
                            "and match pattern " + registrationNumberPattern
            );
        }
    }
    public void validateTaxNumber(String taxNumber) {
        if (taxNumber==null || taxNumber.length()!=taxNumberLength || !taxNumber.matches(taxNumberPattern)){
            throw new RuntimeException(
                    "Tax number for country " + countryCode +
                            " must be " + taxNumberLength + " characters long, " +
                            "and match pattern " + taxNumberPattern
            );
        }
    }
    public void validateCompanyStructure(CompanyStructure structure) {
        if (!companyStructures.contains(structure)){
            throw new RuntimeException(
                    structure + " is unregistered business structure in country " + countryCode + ". ");
        }
    }

}
