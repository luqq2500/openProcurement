package administrator.api;

import administrator.UpdateCompanyRegistrationStatusCommand;

public interface CompanyRegistrationStatusUpdater {
    void update(UpdateCompanyRegistrationStatusCommand command);
}
