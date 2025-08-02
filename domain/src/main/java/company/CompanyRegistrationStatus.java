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
