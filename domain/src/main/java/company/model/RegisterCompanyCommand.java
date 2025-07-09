package company.model;

public record RegisterCompanyCommand(String name, String registrationNumber, String taxNumber, String structure, String countryCode) {
}
