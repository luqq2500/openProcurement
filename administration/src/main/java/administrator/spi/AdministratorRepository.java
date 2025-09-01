package administrator.spi;

import administrator.Administrator;

import java.util.UUID;

public interface AdministratorRepository {
    void add(Administrator administrator);
    Administrator get(UUID administratorId);
}
