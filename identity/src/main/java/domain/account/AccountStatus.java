package domain.account;

public enum AccountStatus {
    ACTIVE, INACTIVE;

    public void validateStatusChangeTo(AccountStatus newStatus) {
        if (newStatus == this) {
            throw new InvalidAccountStatus("Invalid account status");
        }
        if (this.equals(ACTIVE) && !newStatus.equals(INACTIVE)) {
            throw new InvalidAccountStatus("Invalid account status");
        }
        if (this.equals(INACTIVE)) {
            throw new InvalidAccountStatus("Invalid account status");
        }
    }
}
