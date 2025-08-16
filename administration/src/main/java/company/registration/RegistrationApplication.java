package company.registration;

import administrator.Administrator;
import company.RegistrationStatus;
import company.exception.RegistrationApplicationException;

import java.util.UUID;

public class RegistrationApplication {
    private UUID applicationId;
    private RegistrationStatus status;
    private UUID statusUpdateByAdministratorId;

    public RegistrationApplication(UUID applicationId, RegistrationStatus status, UUID statusUpdateByAdministratorId) {
        this.applicationId = applicationId;
        this.status = status;
        this.statusUpdateByAdministratorId = statusUpdateByAdministratorId;
    }

    public void elevateNewStatus(Administrator administrator, RegistrationStatus newStatus) {
        if (!this.status.validateStatusUpdateTo(newStatus)){
            throw new RegistrationApplicationException("New status is invalid.");
        }
        this.status = newStatus;
        this.statusUpdateByAdministratorId = administrator.getAdministratorId();
    }
    public boolean isInProgress() {
        return this.status.equals(RegistrationStatus.IN_PROGRESS);
    }
    public void reject(){
        this.status.validateStatusUpdateTo(RegistrationStatus.REJECTED);
        this.status = RegistrationStatus.REJECTED;
        this.applicationId = null;
    }
}
