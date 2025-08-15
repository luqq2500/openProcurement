package company.accountAdministrator;

import java.util.UUID;

public class AccountAdministrator {
    private final UUID accountId;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
    private final UUID companyId;
    private boolean isActive;
    public AccountAdministrator(String firstName, String lastName, String username, String password, UUID companyId) {
        this.accountId = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.companyId = companyId;
    }
}
