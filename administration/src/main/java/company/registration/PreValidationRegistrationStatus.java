package company.registration;

public enum PreValidationRegistrationStatus {
    IN_PROGRESS, PASSED, FAILED;
    public boolean canElevateStatusTo(PreValidationRegistrationStatus newStatus) {
        if (this.equals(newStatus)) {
            return false;
        }
        if (this.equals(PASSED) || this.equals(FAILED)) {
            return false;
        }
        return !this.equals(IN_PROGRESS) || newStatus.equals(PASSED) || newStatus.equals(FAILED);
    }
}
