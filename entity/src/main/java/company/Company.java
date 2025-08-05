package company;

import address.Address;
import address.Country;
import user.User;

import java.util.Set;
import java.util.UUID;

public class Company {
    private final UUID id;
    private String companyName;
    private final String registrationNumber;
    private final String taxNumber;
    private CompanyStructure structure;
    private Address address;
    private Set<User> users;

    public Company(String companyName, String registrationNumber, String taxNumber, CompanyStructure structure, Address address) {
        this.id = UUID.randomUUID();
        this.companyName = companyName;
        this.registrationNumber = registrationNumber;
        this.taxNumber = taxNumber;
        this.structure = structure;
        this.address = address;
    }
    public String getRegistrationNumber() {
        return registrationNumber;
    }
    public String getTaxNumber() {
        return taxNumber;
    }
}
