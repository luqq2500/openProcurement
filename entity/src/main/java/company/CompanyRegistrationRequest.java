package company;

import company.exception.CompanyRegistrationRequestExpired;

import java.time.LocalDateTime;
import java.util.UUID;

public class CompanyRegistrationRequest {
    private final UUID id;
    private final String email;
    private final LocalDateTime requestDate;
    private final LocalDateTime expiryDate;

    public CompanyRegistrationRequest(String email, LocalDateTime expiryDate) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.requestDate = LocalDateTime.now();
        this.expiryDate = expiryDate;
    }
    public void checkValidity(){
        if (LocalDateTime.now().isAfter(expiryDate)) {
            throw new CompanyRegistrationRequestExpired("Request has already expired on " + expiryDate);
        }
    }
    public UUID getId() {return id;}
    public LocalDateTime getRequestDate() {
        return requestDate;
    }
    public LocalDateTime getExpiryTime() {return expiryDate;}
    public String getEmail() {return email;}
}
