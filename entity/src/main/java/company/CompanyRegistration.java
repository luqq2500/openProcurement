package company;

import address.Address;
import address.Country;
import company.exception.InvalidCompanyRegistrationStatus;

import java.time.LocalDateTime;
import java.util.*;

public class CompanyRegistration {
    private final UUID registrationId;
    private final String email;
    private final String companyName;
    private final Address address;
    private final String registrationNumber;
    private final String taxNumber;
    private final CompanyStructure structure;
    private final CompanyRegistrationStatus status;
    private final LocalDateTime registrationDate;
    private UUID administratorId;
    private String administratorNote;

    public CompanyRegistration(String email, String companyName, Address address, String registrationNumber, String taxNumber, CompanyStructure structure, CompanyRegistrationStatus status) {
        this.registrationId = UUID.randomUUID();
        this.email = email;
        this.companyName = companyName;
        this.address = address;
        this.registrationNumber = registrationNumber;
        this.taxNumber = taxNumber;
        this.structure = structure;
        this.status = status;
        this.registrationDate = LocalDateTime.now();
        this.administratorId = null;
        this.administratorNote = null;
    }

    public CompanyRegistration updateStatus(UUID administratorId, CompanyRegistrationStatus newStatus, String administratorNote) {
        if (!this.status.canUpdateTo(newStatus)){
            throw new InvalidCompanyRegistrationStatus("Invalid update status transaction");
        }
        CompanyRegistration newRegistration = new CompanyRegistration(email, companyName, address, registrationNumber, taxNumber, structure, newStatus);
        newRegistration.setAdministratorId(administratorId);
        newRegistration.setAdministratorNote(administratorNote);
        return newRegistration;
    }
    public String getEmail(){return email;}
    public Address getAddress(){return address;}
    private void setAdministratorId(UUID administratorId) {this.administratorId = administratorId;}
    public void setAdministratorNote(String administratorNote) {this.administratorNote = administratorNote;}
    public String getCompanyName() {return companyName;}
    public String getRegistrationNumber() {return registrationNumber;}
    public String getTaxNumber() {return taxNumber;}
    public CompanyStructure getStructure() {return structure;}
    public Country getCountry() {return address.country();}
    public CompanyRegistrationStatus getStatus() {return status;}
    public UUID getRegistrationId() {return registrationId;}
}
