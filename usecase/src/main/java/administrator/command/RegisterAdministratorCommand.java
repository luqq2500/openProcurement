package administrator.command;

import administrator.exception.InvalidRegisterAdministratorCommand;

public record RegisterAdministratorCommand (String email, String password, String firstName, String lastName, String role){
    public RegisterAdministratorCommand {
        if (email.isBlank()) {
            throw new InvalidRegisterAdministratorCommand("Email cannot be blank");
        }
        if (password.isBlank()) {
            throw new InvalidRegisterAdministratorCommand("Password cannot be blank");
        }
        if (firstName.isBlank()) {
            throw new InvalidRegisterAdministratorCommand("First companyName cannot be blank");
        }
        if (lastName.isBlank()) {
            throw new InvalidRegisterAdministratorCommand("Last companyName cannot be blank");
        }
        if (role.isBlank()) {
            throw new InvalidRegisterAdministratorCommand("Role cannot be blank");
        }
    }
}
