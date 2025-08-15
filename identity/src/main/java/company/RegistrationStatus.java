package company;

import company.exception.InvalidRegistrationApplicationStatus;

public enum RegistrationStatus {
    PENDING,
    PROCESSING,
    APPROVED,
    REJECTED;

    public void validateStatusUpdateTo(RegistrationStatus newStatus) {
        if (this.equals(newStatus)) {
            throw new InvalidRegistrationApplicationStatus("Status is already " + this.toString());
        }
        if (this.equals(PENDING) && !newStatus.equals(PROCESSING)) {
            throw new InvalidRegistrationApplicationStatus(this.toString() + " can only updated to " + PROCESSING.toString());
        }
        if (this.equals(PROCESSING) && !newStatus.equals(APPROVED) && !newStatus.equals(REJECTED)) {
            throw new InvalidRegistrationApplicationStatus(this.toString() + " can only updated to " + APPROVED.toString() + " and " + REJECTED.toString());
        }
        if (this.equals(APPROVED) || this.equals(REJECTED)) {
            throw new InvalidRegistrationApplicationStatus("Status cannot be updated.");
        }
    }
}
