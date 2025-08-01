package administrator.api;
import administrator.dto.UpdateCompanyRegistrationStatusCommand;

public interface CompanyRegistrationStatusUpdater {
    void update(UpdateCompanyRegistrationStatusCommand command);
}
