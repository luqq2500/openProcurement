package user;

import java.util.UUID;

public class User {
    private final UUID id;
    private final String firstName;
    private final String lastName;
    private String email;
    private String password;
    private UserRole role;
    public User(String firstName, String lastName, String email, String password, UserRole role) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public void updateRoleTo(UserRole newRole) {
        this.role=newRole;
    }
    public void updateEmail(String newEmail) {
        this.email = newEmail;
    }
    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
