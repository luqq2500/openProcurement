package company;

import applicant.Applicant;

import java.time.LocalDateTime;
import java.util.UUID;

public class CompanyRegistrationRequest {
    private final String requestId;
    private final Applicant applicant;
    private final LocalDateTime requestDate;
    private LocalDateTime expiryDate;

    public CompanyRegistrationRequest(Applicant applicant, LocalDateTime expiryTime) {
        this.requestId = UUID.randomUUID().toString();
        this.applicant = applicant;
        this.requestDate = LocalDateTime.now();
        this.expiryDate = expiryTime;
    }
    public void checkRequestExpiry(LocalDateTime currentDate) {
        if (currentDate.isAfter(expiryDate)) {
            throw new RuntimeException("Request has already expired on " + expiryDate);
        }
    }
    public String getRequestId() {
        return requestId;
    }
    public Applicant getApplicant() {
        return applicant;
    }
    public LocalDateTime getRequestDate() {
        return requestDate;
    }
    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }
}
