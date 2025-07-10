package company.model;

import user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Company {
    private final UUID id;
    private String name;
    private String registrationNumber;
    private String structure;
    private String taxNumber;
    private String countryCode;
    private List<User> users = new ArrayList<>();

    public Company(String name, String registrationNumber, String taxNumber, String structure, String countryCode) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.structure = structure;
        this.registrationNumber = registrationNumber;
        this.taxNumber = taxNumber;
        this.countryCode = countryCode;
    }

    public UUID getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRegistrationNumber() {
        return registrationNumber;
    }
    public void setRegistrationNumber(String registrationNumber) {this.registrationNumber = registrationNumber; }
    public String getTaxNumber() {
        return taxNumber;
    }
    public void setTaxNumber(String taxNumber) { this.taxNumber = taxNumber; }
    public String getStructure() { return structure; }
    public void setStructure(String structure) { this.structure = structure; }
    public String getCountryCode() {return countryCode;}
    public void setCountryCode(String countryCode) {this.countryCode = countryCode;}
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }

}
