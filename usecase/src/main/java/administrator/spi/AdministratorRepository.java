package administrator.spi;

import administrator.Administrator;

import java.util.List;

public interface AdministratorRepository {
    List<Administrator> administrators();
}
