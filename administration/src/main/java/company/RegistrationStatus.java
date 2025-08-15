package company;

import company.exception.InvalidRegistrationApplicationStatus;

public enum RegistrationStatus {
    SUBMITTED,
    IN_PROGRESS,
    APPROVED,
    REJECTED;

    public boolean validateStatusUpdateTo(RegistrationStatus newStatus) {
        if (this.equals(newStatus)) {return false;}
        if (this.equals(SUBMITTED) && !newStatus.equals(IN_PROGRESS)) {return false;}
        if (this.equals(IN_PROGRESS) && !newStatus.equals(APPROVED) && !newStatus.equals(REJECTED)) {return false;}
        return !this.equals(APPROVED) && !this.equals(REJECTED);
    }
}
