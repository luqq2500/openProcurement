package administrator.api;

import administrator.model.RegisterAdministratorCommand;
import administrator.model.Administrator;

public interface AdministratorRegistrator {
    Administrator register(RegisterAdministratorCommand command);
}
