package administrator;

import administrator.model.Administrator;
import administrator.spi.AdministratorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryAdministratorRepository implements AdministratorRepository {
    private final List<Administrator> administrators = new ArrayList<>();

    @Override
    public void add(Administrator administrator) {
        administrators.add(administrator);
    }

    @Override
    public Optional<Administrator> findByEmail(String email) {
        return administrators.stream().filter(admin -> admin.getEmail().equals(email)).findFirst();
    }
}
