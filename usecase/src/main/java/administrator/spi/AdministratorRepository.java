package administrator.spi;

import administrator.Administrator;

import java.util.Optional;

public interface AdministratorRepository {
    Optional<Administrator> findById(String administratorId);
    void add(Administrator administrator);
}
