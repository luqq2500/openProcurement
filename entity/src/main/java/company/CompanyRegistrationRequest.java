package company;

import company.exception.CompanyRegistrationRequestExpired;

import java.time.LocalDateTime;
import java.util.UUID;

public class CompanyRegistrationRequest {
    private final UUID id;
    private final String email;
    private LocalDateTime requestDate;
    private LocalDateTime expiryDate;
    private boolean enabled = false;
    private String registrationLink;

    public CompanyRegistrationRequest(String email) {
        this.id = UUID.randomUUID();
        this.email = email;
    }
    public void enable(String registrationLink, LocalDateTime expiryDate) {
        this.enabled = true;
        this.registrationLink = registrationLink;
        this.requestDate = LocalDateTime.now();
        this.expiryDate = expiryDate;
    }
    public void checkValidity(){
        if (LocalDateTime.now().isAfter(expiryDate)) {
            this.enabled = false;
            throw new CompanyRegistrationRequestExpired("Request has already expired on " + expiryDate);
        }
    }
    public boolean isEnabled(){return this.enabled;}
    public UUID getId() {return id;}
    public LocalDateTime getRequestDate() {
        return requestDate;
    }
    public LocalDateTime getExpiryTime() {return expiryDate;}
    public String getEmail() {return email;}

    public String getRegistrationLink() {
        return registrationLink;
    }
}
