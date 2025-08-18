package company.registration;

import java.util.UUID;

public class RegistrationPreValidation {
    private final UUID registrationId;
    private final String companyName;
    private final String brn;
    private final String taxNumber;
    private PreValidationRegistrationStatus status;
    public RegistrationPreValidation(UUID registrationId, String companyName, String brn, String taxNumber) {
        this.registrationId = registrationId;
        this.companyName = companyName;
        this.brn = brn;
        this.taxNumber = taxNumber;
        this.status = PreValidationRegistrationStatus.IN_PROGRESS;
    }
    public void updateStatusTo(PreValidationRegistrationStatus status) {
        if (this.status.canElevateStatusTo(status)) {
            this.status = status;
        }
    }
    public UUID getRegistrationId() {
        return registrationId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getBrn() {
        return brn;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public PreValidationRegistrationStatus getStatus() {
        return status;
    }
}
