package company;

import address.CountryCode;
import user.User;

import java.util.Set;

public class Company {
    private String companyName;
    private final String registrationNumber;
    private final String taxNumber;
    private CompanyStructure structure;
    private CountryCode countryCode;
    private Set<User> users;

    public Company(String companyName, String registrationNumber, String taxNumber, CompanyStructure structure, CountryCode countryCode) {
        this.companyName = companyName;
        this.registrationNumber = registrationNumber;
        this.taxNumber = taxNumber;
        this.structure = structure;
        this.countryCode = countryCode;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }
    public String getTaxNumber() {
        return taxNumber;
    }
}
