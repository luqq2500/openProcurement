package administrator.dto;

import company.CompanyRegistrationStatus;

public record UpdateCompanyRegistrationStatusCommand(
        String administratorId,
        String companyRegistrationNumber,
        CompanyRegistrationStatus status,
        String notes) {
}
