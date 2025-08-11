package company;

import company.exception.InvalidCompanyRegistrationStatus;

public enum CompanyRegistrationStatus {
    PENDING,
    PROCESSING,
    APPROVED,
    REJECTED;

    public void checkChangeStatusTo(CompanyRegistrationStatus newStatus) {
        if (this.equals(newStatus)) {
            throw new InvalidCompanyRegistrationStatus("Company registration status is already " + newStatus);
        }
        if (this.equals(PENDING) && newStatus != PROCESSING) {
            throw new InvalidCompanyRegistrationStatus("Company registration status can only be updated to " + PROCESSING);
        }
        if (this.equals(PROCESSING) && newStatus != APPROVED && newStatus != REJECTED) {
            throw new InvalidCompanyRegistrationStatus("Processing status can only be updated to " + APPROVED + " or " + REJECTED);
        }
        if (this.equals(APPROVED) || this.equals(REJECTED)) {
            throw new InvalidCompanyRegistrationStatus(APPROVED + " and " + REJECTED + " status cannot be updated.");
        }
    }
    public boolean isPending() {
        return this == PENDING;
    }
    public boolean isProcessing() {
        return this == PROCESSING;
    }
    public boolean isApproved() {
        return this == APPROVED;
    }
    public boolean isRejected() {
        return this == REJECTED;
    }
}
