package administrator.api;

import administrator.command.RegisterAdministratorCommand;
import administrator.model.Administrator;

@FunctionalInterface
public interface AdministratorRegistrator {
    Administrator register(RegisterAdministratorCommand command);
}
