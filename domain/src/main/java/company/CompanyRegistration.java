package company;

import address.CountryCode;

import java.time.LocalDateTime;
import java.util.*;

public class CompanyRegistration {
    private final String registrationId;
    private final String companyName;
    private final String registrationNumber;
    private final String taxNumber;
    private final CompanyStructure structure;
    private final CountryCode countryCode;
    private final CompanyRegistrationStatus status;
    private final LocalDateTime registrationDate;
    private String administratorId;
    private String administratorNote;

    public CompanyRegistration(String companyName, String registrationNumber, String taxNumber, CompanyStructure structure, CountryCode countryCode, CompanyRegistrationStatus status) {
        this.registrationId = UUID.randomUUID().toString();
        this.companyName = companyName;
        this.registrationNumber = registrationNumber;
        this.taxNumber = taxNumber;
        this.structure = structure;
        this.countryCode = countryCode;
        this.registrationDate = LocalDateTime.now();
        this.status = status;
        this.administratorId = null;
        this.administratorNote = null;
    }

    public CompanyRegistration updateStatus(String administratorId, CompanyRegistrationStatus newStatus, String administratorNote) {
        if (!this.status.canUpdateTo(newStatus)){
            throw new RuntimeException("Invalid update status transaction");
        }
        CompanyRegistration newRegistration = new CompanyRegistration(companyName, registrationNumber, taxNumber, structure, countryCode, newStatus);
        newRegistration.setAdministratorId(administratorId);
        newRegistration.setAdministratorNote(administratorNote);
        return newRegistration;
    }

    private void setAdministratorId(String administratorId) {this.administratorId = administratorId;}
    public void setAdministratorNote(String administratorNote) {this.administratorNote = administratorNote;}
    public String getCompanyName() {return companyName;}
    public String getRegistrationNumber() {return registrationNumber;}
    public String getTaxNumber() {return taxNumber;}
    public CompanyStructure getStructure() {return structure;}
    public CountryCode getCountryCode() {return countryCode;}
    public LocalDateTime getRegistrationDate() {return registrationDate;}
    public CompanyRegistrationStatus getStatus() {return status;}
    public String getRegistrationId() {return registrationId;}
}
