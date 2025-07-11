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
        validateEmail(command);
        Administrator administrator = new Administrator(
                command.email(),
                command.password(),
                command.firstName(),
                command.lastName()
        );
        this.repository.add(administrator);
        return administrator;
    }

    private void validateEmail(RegisterAdministratorCommand command) {
        if (repository.findByEmail(command.email()).isPresent()){
            throw new AdministratorAlreadyExistException("Administrator " + command.email() + " is already exist.");
        }
    }
}
