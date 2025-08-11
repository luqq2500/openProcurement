package administrator;

import company.CompanyRegistrationStatus;

import java.time.Instant;

public record UpdateCompanyRegistrationStatusResponse(
        String companyName, CompanyRegistrationStatus newStatus, Instant updatedAt) {
}
