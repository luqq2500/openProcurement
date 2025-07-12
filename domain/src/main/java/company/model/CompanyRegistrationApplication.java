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
        String approvalFromAdministratorId,
        LocalDateTime approvalTime
) {
    public CompanyRegistrationApplication withStatus(String newStatus, LocalDateTime newApprovalTime) {
        return new CompanyRegistrationApplication(applicationId, companyName, registrationNumber, taxNumber,
                businessStructure, countryCode, applicationTime, newStatus, approvalFromAdministratorId, newApprovalTime);
    }

    public CompanyRegistrationApplication withAdministratorId(String adminId) {
        return new CompanyRegistrationApplication(applicationId, companyName, registrationNumber, taxNumber,
                businessStructure, countryCode, applicationTime, status, adminId, approvalTime);
    }
}