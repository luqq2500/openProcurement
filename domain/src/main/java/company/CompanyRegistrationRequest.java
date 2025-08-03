package company;

import applicant.Applicant;

import java.time.LocalDateTime;
import java.util.UUID;

public class CompanyRegistrationRequest {
    private final String requestId;
    private final Applicant applicant;
    private final LocalDateTime requestDate;
    private final LocalDateTime expiryDate;

    public CompanyRegistrationRequest(Applicant applicant) {
        this.requestId = UUID.randomUUID().toString();
        this.applicant = applicant;
        this.requestDate = LocalDateTime.now();
        this.expiryDate = LocalDateTime.now().plusDays(3);
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
