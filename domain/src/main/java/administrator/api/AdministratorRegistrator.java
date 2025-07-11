package administrator.api;

import administrator.model.RegisterAdministratorCommand;
import administrator.model.Administrator;

@FunctionalInterface
public interface AdministratorRegistrator {
    Administrator register(RegisterAdministratorCommand command);
}
