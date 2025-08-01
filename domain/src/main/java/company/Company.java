package company;

import user.User;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class Company {
    private String companyName;
    private String registrationNumber;
    private String taxNumber;
    private CompanyStructure structure;
    private Locale.IsoCountryCode countryCode;
    private Set<User> users;

    public Company(String companyName, String registrationNumber, String taxNumber, CompanyStructure structure) {
        this.companyName = companyName;
        this.registrationNumber = registrationNumber;
        this.taxNumber = taxNumber;
        this.structure = structure;
        this.countryCode = null;
    }
}
