package domain.registration;

import domain.registration.exception.InvalidRegistrationApplicationStatus;

public enum RegistrationApplicationStatus {
    UNDER_REVIEW, REJECTED, APPROVED;
    public boolean checkStatusChangeTo(RegistrationApplicationStatus newStatus) {
        if (this.equals(newStatus)) {
            return false;
        }
        if (this.equals(UNDER_REVIEW) && !newStatus.equals(APPROVED) && !newStatus.equals(REJECTED)) {
            return false;
        }
        if (this.equals(REJECTED) && !newStatus.equals(UNDER_REVIEW)) {
            return false;
        }
        return !this.equals(APPROVED);
    }
}
