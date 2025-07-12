package administrator.spi;

import administrator.model.Administrator;

import java.util.List;

@FunctionalInterface
public interface AdministratorRepository {
    List<Administrator> administrators();
}
