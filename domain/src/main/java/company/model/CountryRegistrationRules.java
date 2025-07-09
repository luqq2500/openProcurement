package company.model;
import company.exception.InvalidCountryRegistrationRulesException;

public record CountryRegistrationRules(
        String countryCode,
        int registrationNumberLength,
        String registrationNumberPattern,
        int taxNumberLength,
        String taxNumberPattern) {

    public void validateRegistrationNumber(String registrationNumber) {
        if (registrationNumber==null || registrationNumber.length()!=registrationNumberLength || !registrationNumber.matches(registrationNumberPattern)){
            throw new InvalidCountryRegistrationRulesException(
                    "Registration number for country {" + countryCode +
                            "} must be " + registrationNumberLength + " characters long, " +
                            "and match pattern " + registrationNumberPattern);
        }
    }
    public void validateTaxNumber(String taxNumber) {
        if (taxNumber==null || taxNumber.length()!=taxNumberLength || !taxNumber.matches(taxNumberPattern)){
            throw new InvalidCountryRegistrationRulesException(
                    "Tax number for country {" + countryCode +
                            "} must be " + taxNumberLength + " characters long, " +
                            "and match pattern " + taxNumberPattern);
        }
    }

}
