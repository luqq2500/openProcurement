package company.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Company {
    private final String companyId;
    private String companyName;
    private String registrationNumber;
    private String businessStructure;
    private String taxNumber;
    private String countryCode;
    private List<User> users = new ArrayList<>();

    public Company(String companyName, String registrationNumber, String taxNumber, String businessStructure, String countryCode) {
        this.companyId = UUID.randomUUID().toString();
        this.companyName = companyName;
        this.businessStructure = businessStructure;
        this.registrationNumber = registrationNumber;
        this.taxNumber = taxNumber;
        this.countryCode = countryCode;
    }

    public String getCompanyId() {
        return companyId;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getRegistrationNumber() {
        return registrationNumber;
    }
    public void setRegistrationNumber(String registrationNumber) {this.registrationNumber = registrationNumber; }
    public String getTaxNumber() {
        return taxNumber;
    }
    public void setTaxNumber(String taxNumber) { this.taxNumber = taxNumber; }
    public String getBusinessStructure() { return businessStructure; }
    public void setBusinessStructure(String businessStructure) { this.businessStructure = businessStructure; }
    public String getCountryCode() {return countryCode;}
    public void setCountryCode(String countryCode) {this.countryCode = countryCode;}
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }

}
