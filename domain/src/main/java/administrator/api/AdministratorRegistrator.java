package administrator.api;

import administrator.application.command.RegisterAdministratorCommand;
import administrator.model.Administrator;

@FunctionalInterface
public interface AdministratorRegistrator {
    Administrator register(RegisterAdministratorCommand command);
}
