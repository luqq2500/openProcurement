package company;

public enum RegistrationStatus {
    IN_PROGRESS,
    APPROVED,
    REJECTED;

    public boolean validateStatusUpdateTo(RegistrationStatus newStatus) {
        if (this.equals(newStatus)) {return false;}
        return !this.equals(APPROVED) && !this.equals(REJECTED);
    }
}
