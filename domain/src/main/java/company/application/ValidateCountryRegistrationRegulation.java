package company.application;

import company.exception.InvalidCountryRegistrationRulesException;
import company.model.CountryRegistrationRules;
import company.spi.CountryRegistrationRulesRepository;

public class ValidateCountryRegistrationRegulation {
    private final CountryRegistrationRulesRepository countryRegistrationRulesRepository;

    public ValidateCountryRegistrationRegulation(CountryRegistrationRulesRepository countryRegistrationRulesRepository) {
        this.countryRegistrationRulesRepository = countryRegistrationRulesRepository;
    }

    public void validateCompanyDetails(String countryCode, String registrationNumber, String taxNumber){
        CountryRegistrationRules countryRegistrationRules = countryRegistrationRulesRepository.findByCountryCode(countryCode)
                .orElseThrow(() -> new InvalidCountryRegistrationRulesException("No rules found for country: " + countryCode));
        countryRegistrationRules.validateRegistrationNumber(registrationNumber);
        countryRegistrationRules.validateTaxNumber(taxNumber);
    }
}
