package registration;

public enum RegistrationStatus {
    PENDING, APPROVE, REJECT;

    public boolean validateStatusSetTo(RegistrationStatus newStatus) {
        if (this.equals(newStatus)) {
            return false;
        }
        if (this.equals(PENDING) && !newStatus.equals(APPROVE) && !newStatus.equals(REJECT)) {
            return false;
        }
        return !this.equals(APPROVE) && !this.equals(REJECT);
    }
}
