package administrator;

import company.CompanyRegistrationStatus;

import java.util.UUID;

public record UpdateCompanyRegistrationStatusCommand(
        UUID administratorId,
        String companyRegistrationNumber,
        CompanyRegistrationStatus status,
        String notes) {
}
