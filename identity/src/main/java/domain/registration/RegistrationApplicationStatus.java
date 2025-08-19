package domain.registration;

import domain.registration.exception.InvalidRegistrationApplicationStatus;

public enum RegistrationApplicationStatus {
    UNDER_REVIEW, REJECTED, APPROVED;
    public void checkStatusChangeTo(RegistrationApplicationStatus newStatus) {
        if (this.equals(newStatus)) {
            throw new InvalidRegistrationApplicationStatus("Status is already " + this.name());
        }
        if (this.equals(UNDER_REVIEW) && !newStatus.equals(APPROVED) && !newStatus.equals(REJECTED)) {
            throw new InvalidRegistrationApplicationStatus(this.name() + " status cannot be elevated to " + newStatus.name());
        }
        if (this.equals(APPROVED)) {
            throw new InvalidRegistrationApplicationStatus(this.name() + " status cannot be elevated to " + newStatus.name());
        }
    }
}
