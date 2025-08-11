package applicant.api;

import applicant.dto.RequestCompanyRegistrationResponse;

public interface CompanyRegistrationVerificationRequestor {
    RequestCompanyRegistrationResponse requestFrom(String from);
}
