package applicant.api;

import applicant.dto.RequestCompanyRegistrationCommand;

public interface CompanyRegistrationRequestor {
    void request(RequestCompanyRegistrationCommand command);
}
