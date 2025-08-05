package company;

import address.Country;
import company.exception.InvalidCompanyRegistrationNumber;
import company.exception.InvalidCompanyStructure;
import company.exception.InvalidCompanyTaxNumber;

import java.util.List;

public record CompanyCountryRegistrationRule (
        Country country,
        int registrationNumberLength,
        String registrationNumberPattern,
        int taxNumberLength,
        String taxNumberPattern,
        List<CompanyStructure> companyStructures){
    public void validateRegistrationNumber(String registrationNumber) {
        if (registrationNumber==null || registrationNumber.length()!=registrationNumberLength || !registrationNumber.matches(registrationNumberPattern)){
            throw new InvalidCompanyRegistrationNumber(
                    "Registration number for country " + country +
                            " must be " + registrationNumberLength + " characters long, " +
                            "and match pattern " + registrationNumberPattern
            );
        }
    }
    public void validateTaxNumber(String taxNumber) {
        if (taxNumber==null || taxNumber.length()!=taxNumberLength || !taxNumber.matches(taxNumberPattern)){
            throw new InvalidCompanyTaxNumber(
                    "Tax number for country " + country +
                            " must be " + taxNumberLength + " characters long, " +
                            "and match pattern " + taxNumberPattern
            );
        }
    }
    public void validateCompanyStructure(CompanyStructure structure) {
        if (!companyStructures.contains(structure)){
            throw new InvalidCompanyStructure(
                    structure + " is unregistered business structure in country " + country + ". ");
        }
    }

}
