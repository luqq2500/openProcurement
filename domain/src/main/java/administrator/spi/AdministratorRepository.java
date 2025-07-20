package administrator.spi;

import administrator.Administrator;

import java.util.List;

@FunctionalInterface
public interface AdministratorRepository {
    List<Administrator> administrators();
}
