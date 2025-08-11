package administrator;

import company.CompanyRegistrationStatus;

import java.util.UUID;

public record UpdateCompanyRegistrationStatusRequest(
        UUID administratorId,
        UUID companyRegistrationId,
        CompanyRegistrationStatus status,
        String notes
){}
