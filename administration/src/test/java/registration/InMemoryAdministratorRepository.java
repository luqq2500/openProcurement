package registration;

import administrator.Administrator;
import administrator.spi.AdministratorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryAdministratorRepository implements AdministratorRepository {
    private final List<Administrator> administrators = new ArrayList<>();
    @Override
    public void add(Administrator administrator) {
        administrators.add(administrator);
    }
    @Override
    public Administrator get(UUID administratorId) {
        return administrators.stream()
                .filter(administrator -> administrator.getAdministratorId().equals(administratorId))
                .findFirst()
                .orElse(null);
    }
}
