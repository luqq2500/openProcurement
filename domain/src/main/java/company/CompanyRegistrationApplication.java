package company;

import address.AddressCommand;

import java.time.LocalDateTime;

public record CompanyRegistrationApplication(
        String applicationId,
        String companyName,
        String registrationNumber,
        String taxNumber,
        String businessStructure,
        AddressCommand address,
        LocalDateTime applicationTime,
        String status,
        String approvedBy,
        LocalDateTime approvalTime
) {
    public CompanyRegistrationApplication updateStatus(String newStatus, String adjudicatorId) {
        return new CompanyRegistrationApplication(applicationId, companyName, registrationNumber, taxNumber,
                businessStructure, address, applicationTime, newStatus, adjudicatorId, LocalDateTime.now());
    }
}