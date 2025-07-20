package administrator.api;

import administrator.command.RegisterAdministratorCommand;
import administrator.Administrator;

@FunctionalInterface
public interface AdministratorRegistrator {
    Administrator register(RegisterAdministratorCommand command);
}
