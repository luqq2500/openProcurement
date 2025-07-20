package administrator;

import administrator.api.AdministratorRegistrator;
import administrator.command.RegisterAdministratorCommand;
import administrator.exception.AdministratorAlreadyExistException;
import administrator.spi.AdministratorRepository;

public class RegisterAdministrator implements AdministratorRegistrator {
    private final AdministratorRepository repository;

    public RegisterAdministrator(AdministratorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Administrator register(RegisterAdministratorCommand command) {
        validateEmailIsUnique(command);
        return new Administrator(
                command.email(),
                command.password(),
                command.firstName(),
                command.lastName(),
                command.role()
        );
    }
    private void validateEmailIsUnique(RegisterAdministratorCommand command) throws AdministratorAlreadyExistException {
        if (repository.administrators().stream().anyMatch(administrator -> administrator.getEmail().equals(command.email()))) {
            throw new AdministratorAlreadyExistException("Authorizer " + command.email() + " is already exist.");
        }
    }
}
