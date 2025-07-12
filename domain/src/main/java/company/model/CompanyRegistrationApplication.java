package company.model;

import java.time.LocalDateTime;

public record CompanyRegistrationApplication(
        String applicationId,
        String companyName,
        String registrationNumber,
        String taxNumber,
        String businessStructure,
        String countryCode,
        LocalDateTime applicationTime,
        String status,
        String approvedBy,
        LocalDateTime approvalTime
) {
    public CompanyRegistrationApplication updateStatus(String newStatus, String adjudicatorId) {
        return new CompanyRegistrationApplication(applicationId, companyName, registrationNumber, taxNumber,
                businessStructure, countryCode, applicationTime, newStatus, adjudicatorId, LocalDateTime.now());
    }
}