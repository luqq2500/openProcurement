package company;

import user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Company {
    private final UUID id;
    private String companyName;
    private String companyStructure;
    private String businessRegistrationNumber;
    private String taxIdentificationNumber;
    private List<User> users = new ArrayList<>();

    public Company(String companyName, String companyStructure, String businessRegistrationNumber, String taxIdentificationNumber) {
        this.id = UUID.randomUUID();
        this.companyName = companyName;
        this.companyStructure = companyStructure;
        this.businessRegistrationNumber = businessRegistrationNumber;
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public UUID getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBusinessRegistrationNumber() {
        return businessRegistrationNumber;
    }

    public void setBusinessRegistrationNumber(String businessRegistrationNumber) {
        this.businessRegistrationNumber = businessRegistrationNumber;
    }

    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
