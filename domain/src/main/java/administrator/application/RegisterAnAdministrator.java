package administrator.application;

import administrator.api.AdministratorRegistrator;
import administrator.exception.AdministratorAlreadyExistException;
import administrator.model.Administrator;
import administrator.model.RegisterAdministratorCommand;
import administrator.spi.AdministratorRepository;

public class RegisterAnAdministrator implements AdministratorRegistrator {
    private final AdministratorRepository repository;

    public RegisterAnAdministrator(AdministratorRepository repository) {
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
        if (repository.administrators().anyMatch(administrator -> administrator.getEmail().equals(command.email()))) {
            throw new AdministratorAlreadyExistException("Administrator " + command.email() + " is already exist.");
        }
    }
}
