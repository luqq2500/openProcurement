package company;

public enum CompanyRegistrationStatus {
    PENDING,
    PROCESSING,
    APPROVED,
    REJECTED;

    public boolean canUpdateTo(CompanyRegistrationStatus newStatus) {
        if (newStatus == null) {
            return false;
        }
        return switch (this) {
            case PENDING -> newStatus == PROCESSING;
            case PROCESSING -> newStatus == APPROVED || newStatus == REJECTED;
            default -> false;
        };
    }
}
