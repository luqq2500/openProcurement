package administrator.spi;

import administrator.Administrator;

import java.util.Optional;
import java.util.UUID;

public interface AdministratorRepository {
    Administrator getById(UUID id);
    void add(Administrator administrator);
}
