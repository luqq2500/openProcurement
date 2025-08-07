package company;

import company.exception.CompanyRegistrationRequestExpired;

import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.UUID;

public class CompanyRegistrationRequest {
    private final UUID id;
    private final String email;
    private LocalDateTime requestDate;
    private LocalDateTime expiryDate;
    private boolean enabled = false;

    public CompanyRegistrationRequest(String email) {
        this.id = UUID.randomUUID();
        this.email = email;
    }
    public void enable(LocalDateTime requestDate, LocalDateTime expiryDate) {
        this.enabled = true;
        this.requestDate = requestDate;
        this.expiryDate = expiryDate;
    }
    public boolean isEnabled(){
        return enabled;
    }
    private void checkExpiry(LocalDateTime currentDate) {
        if (currentDate.isAfter(expiryDate)) {
            this.enabled = false;
            throw new CompanyRegistrationRequestExpired("Request has already expired on " + expiryDate);
        }
    }
    public UUID getId() {return id;}
    public LocalDateTime getRequestDate() {
        return requestDate;
    }
    public LocalDateTime getExpiryTime() {
        return expiryDate;
    }
    public String getEmail() {return email;}
}
