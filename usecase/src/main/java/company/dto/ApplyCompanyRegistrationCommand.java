package company.dto;

import address.CountryCode;
import company.CompanyStructure;

import java.util.Objects;

public record ApplyCompanyRegistrationCommand(
        String companyName,
        String registrationNumber,
        String taxNumber,
        CompanyStructure structure,
        CountryCode countryCode
) {
    public ApplyCompanyRegistrationCommand {
        Objects.requireNonNull(companyName);
        Objects.requireNonNull(registrationNumber);
        Objects.requireNonNull(taxNumber);
        Objects.requireNonNull(structure);
        Objects.requireNonNull(countryCode);
    }
}
