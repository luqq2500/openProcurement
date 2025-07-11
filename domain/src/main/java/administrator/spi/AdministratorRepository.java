package administrator.spi;

import administrator.model.Administrator;

import java.util.Optional;

public interface AdministratorRepository {
    void add(Administrator administrator);
    Optional<Administrator> findByEmail(String email);
}
