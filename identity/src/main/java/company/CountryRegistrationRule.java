package company;

import company.address.Country;
import company.exception.CountryRegistrationRuleException;

import java.util.List;

public record CountryRegistrationRule(
        Country country,
        int registrationNumberLength,
        String registrationNumberPattern,
        int taxNumberLength,
        String taxNumberPattern,
        List<Structure> companyStructures
) {
    public void validateRegistrationNumber(String registrationNumber) {
        if (registrationNumber==null || registrationNumber.length()!=registrationNumberLength || !registrationNumber.matches(registrationNumberPattern)){
            throw new CountryRegistrationRuleException(
                    "Registration number for country " + country +
                            " must be " + registrationNumberLength + " characters long, " +
                            "and match pattern " + registrationNumberPattern
            );
        }
    }
    public void validateTaxNumber(String taxNumber) {
        if (taxNumber==null || taxNumber.length()!=taxNumberLength || !taxNumber.matches(taxNumberPattern)){
            throw new CountryRegistrationRuleException(
                    "Tax number for country " + country +
                            " must be " + taxNumberLength + " characters long, " +
                            "and match pattern " + taxNumberPattern
            );
        }
    }
    public void validateCompanyStructure(Structure structure) {
        if (!companyStructures.contains(structure)){
            throw new CountryRegistrationRuleException(
                    structure + " is unregistered business structure in country " + country + ". ");
        }
    }
}
