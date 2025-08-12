package company;

public enum CompanyRegistrationApplicationStatus {
    PENDING,
    PROCESSING,
    APPROVED,
    REJECTED;

    public void validateStatusUpdateTo(CompanyRegistrationApplicationStatus newStatus) {
        if (this.equals(newStatus)) {
            throw new InvalidCompanyRegistrationApplicationStatus("Status is already " + this.toString());
        }
        if (this.equals(PENDING) && !newStatus.equals(PROCESSING)) {
            throw new InvalidCompanyRegistrationApplicationStatus(this.toString() + " can only updated to " + PROCESSING.toString());
        }
        if (this.equals(PROCESSING) && !newStatus.equals(APPROVED) && !newStatus.equals(REJECTED)) {
            throw new InvalidCompanyRegistrationApplicationStatus(this.toString() + " can only updated to " + APPROVED.toString() + " and " + REJECTED.toString());
        }
        if (newStatus.equals(APPROVED) || newStatus.equals(REJECTED)) {
            throw new InvalidCompanyRegistrationApplicationStatus(newStatus.toString() + " status cannot be updated.");
        }
    }
}
