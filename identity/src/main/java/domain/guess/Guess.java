package domain.guess;

import domain.account.AccountStatus;

import java.util.UUID;

public class Guess {
    private final UUID id;
    private String email;
    private String password;
    private AccountStatus status;
    public Guess(String email, String password) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.password = password;
        this.status = AccountStatus.ACTIVE;
    }
    public void updatePassword(String password) {
        this.password = password;
    }
    public void updateEmail(String email) {
        this.email = email;
    }
    public void terminateAccount() {
        this.status.validateStatusChangeTo(AccountStatus.INACTIVE);
        this.status = AccountStatus.INACTIVE;
    }
    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
