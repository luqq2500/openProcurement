package applicant.api;

import applicant.dto.RequestCompanyRegistrationRequest;
import applicant.dto.RequestCompanyRegistrationResponse;

public interface CompanyRegistrationRequestor {
    RequestCompanyRegistrationResponse request(RequestCompanyRegistrationRequest request);
}
