package domain.registration;

import domain.registration.exception.InvalidRegistrationApplicationStatus;

public enum RegistrationApplicationStatus {
   IN_PROGRESS, REJECTED, APPROVED;
    public void checkStatusChangeTo(RegistrationApplicationStatus newStatus) {
        if (this.equals(newStatus)) {
            throw new InvalidRegistrationApplicationStatus("Status is already " + this.name());
        }
        if (this.equals(IN_PROGRESS) && !newStatus.equals(APPROVED) && !newStatus.equals(REJECTED)) {
            throw new InvalidRegistrationApplicationStatus(this.name() + " status cannot be elevated to " + newStatus.name());
        }
        if (this.equals(REJECTED) || this.equals(APPROVED)) {
            throw new InvalidRegistrationApplicationStatus(this.name() + " status cannot be elevated to " + newStatus.name());
        }
    }
}
