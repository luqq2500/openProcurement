package administrator;

import administrator.exception.AdministratorNotFoundException;
import administrator.spi.AdministratorRepository;
import ddd.Stub;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Stub
public class MockAdministratorRepository implements AdministratorRepository {
    List<Administrator> administrators = new ArrayList<>();
    @Override
    public Administrator getById(UUID administratorId) {
        return administrators.stream()
                .filter(admin -> admin.getAdministratorId()
                        .equals(administratorId)).findFirst()
                .orElseThrow( () -> new AdministratorNotFoundException("Administrator not found."));
    }
    @Override
    public void add(Administrator administrator) {
        administrators.add(administrator);
    }
}
