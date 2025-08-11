package administrator.api;

import administrator.UpdateCompanyRegistrationStatusRequest;
import administrator.UpdateCompanyRegistrationStatusResponse;

public interface CompanyRegistrationStatusUpdater {
    UpdateCompanyRegistrationStatusResponse update(UpdateCompanyRegistrationStatusRequest command);
}
