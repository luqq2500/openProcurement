package sys;

import java.util.UUID;

public interface CompanyRegistrationPreValidator {
    void preValidate(UUID registrationId, String brn, String taxNumber);
}
