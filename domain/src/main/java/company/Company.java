package company;

import address.AddressCommand;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private final String companyId;
    private String companyName;
    private String registrationNumber;
    private String businessStructure;
    private String taxNumber;
    private AddressCommand addressCommand;
    private List<User> users = new ArrayList<>();

    public Company(String companyId, String companyName, String registrationNumber, String taxNumber, String businessStructure, AddressCommand addressCommand) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.businessStructure = businessStructure;
        this.registrationNumber = registrationNumber;
        this.taxNumber = taxNumber;
        this.addressCommand = addressCommand;
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
    public AddressCommand getAddress() {return addressCommand;}
    public void setAddress(AddressCommand addressCommand) {this.addressCommand = addressCommand;}
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }

}
