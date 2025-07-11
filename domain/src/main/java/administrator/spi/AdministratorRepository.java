package administrator.spi;

import administrator.model.Administrator;

import java.util.stream.Stream;

@FunctionalInterface
public interface AdministratorRepository {
    Stream<Administrator> administrators();
}
