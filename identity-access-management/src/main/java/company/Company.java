package company;

import company.address.Address;
import user.User;

import java.util.List;
import java.util.UUID;

public class Company {
    private final UUID id;
    private final String name;
    private final Address address;
    private final String businessRegistrationNumber;
    private final Structure structure;
    private final String taxNumber;
    private List<User> users;
    private boolean isActive;

    public Company(String name, Address address, String businessRegistrationNumber, String taxNumber, Structure structure) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.address = address;
        this.businessRegistrationNumber = businessRegistrationNumber;
        this.taxNumber = taxNumber;
        this.structure = structure;
        this.isActive = true;
    }
    public void addNewUser(User user){
        users.add(user);
    }
    public void removeUser(User user){
        users.remove(user);
    }
    public List<User> getUsers() {
        return users;
    }
    public void terminateAccount(){
        isActive = false;
    }
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public String getBusinessRegistrationNumber() {
        return businessRegistrationNumber;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public boolean isActive() {
        return isActive;
    }
}
