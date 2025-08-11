package company;

import address.Address;
import address.Country;
import administrator.Administrator;
import company.exception.InvalidCompanyRegistrationStatus;

import java.time.LocalDateTime;
import java.util.*;

public class CompanyRegistration {
    private final UUID id;
    private final String email;
    private final String companyName;
    private final Address address;
    private final String registrationNumber;
    private final String taxNumber;
    private final CompanyStructure structure;
    private CompanyRegistrationStatus status;
    private final LocalDateTime registrationDate;
    private UUID administratorId;
    private String administratorNote;

    public CompanyRegistration(String email, String companyName, Address address, String registrationNumber, String taxNumber, CompanyStructure structure, CompanyRegistrationStatus status) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.companyName = companyName;
        this.address = address;
        this.registrationNumber = registrationNumber;
        this.taxNumber = taxNumber;
        this.structure = structure;
        this.status = status;
        this.registrationDate = LocalDateTime.now();
    }

    public CompanyRegistration updateStatus(UUID administratorId, CompanyRegistrationStatus newStatus, String administratorNote) {
        this.status.checkChangeStatusTo(newStatus);
        CompanyRegistration newRegistration = new CompanyRegistration(email, companyName, address, registrationNumber, taxNumber, structure, newStatus);
        newRegistration.setAdministratorId(administratorId);
        newRegistration.setAdministratorNote(administratorNote);
        return newRegistration;
    }

    public CompanyRegistration elevateStatusToProcessing(Administrator administrator) {
        this.status.checkChangeStatusTo(CompanyRegistrationStatus.PROCESSING);
        this.administratorId = administrator.getAdministratorId();
        this.status = CompanyRegistrationStatus.PROCESSING;
        return this;
    };

    public CompanyRegistration elevateStatusToApproved(Administrator administrator) {
        this.status.checkChangeStatusTo(CompanyRegistrationStatus.APPROVED);
        this.administratorId = administrator.getAdministratorId();
        this.status = CompanyRegistrationStatus.APPROVED;
        return this;
    }

    public CompanyRegistration elevateStatusToRejected(Administrator administrator) {
        this.status.checkChangeStatusTo(CompanyRegistrationStatus.REJECTED);
        this.administratorId = administrator.getAdministratorId();
        this.status = CompanyRegistrationStatus.REJECTED;
        return this;
    }

    public void addNoteForStatusChange(String note) {
        this.administratorNote = note;
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
    public UUID getId() {return id;}
}
