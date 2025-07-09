package company.model;

import user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Company {
    private final UUID id;
    private String name;
    private String structure;
    private String brn;
    private String tin;
    private List<User> users = new ArrayList<>();

    public Company(String name, String structure, String brn, String tin) {
        validateFormat(name, structure, brn, tin);
        this.id = UUID.randomUUID();
        this.name = name;
        this.structure = structure;
        this.brn = brn;
        this.tin = tin;
    }

    private void validateFormat(String name, String structure, String brn, String tin) {
        if (name == null || name.isBlank()) {
            throw new InvalidCompanyException("Company name cannot be empty");
        }
        if (structure == null || structure.isBlank()) {
            throw new InvalidCompanyException("Company structure cannot be empty");
        }
        if (brn == null || brn.isBlank()) {
            throw new InvalidCompanyException("Business registration number cannot be empty");
        }
        if (tin == null || tin.isBlank()) {
            throw new InvalidCompanyException("Tax identification number cannot be empty");
        }
        if (!CompanyStructure.COMPANY_STRUCTURES.contains(structure)) {
            throw new InvalidCompanyException("Invalid company structure: " + structure);
        }
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
    public String getStructure() { return structure; }
    public void setStructure(String structure) { this.structure = structure; }
    public String getBrn() {
        return brn;
    }
    public void setBrn(String brn) {this.brn = brn; }
    public String getTin() {
        return tin;
    }
    public void setTin(String tin) { this.tin = tin; }
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }
}
