package administrator;

import administrator.spi.AdministratorRepository;
import ddd.Stub;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Stub
public class MockAdministratorRepository implements AdministratorRepository {
    List<Administrator> administrators = new ArrayList<>();
    @Override
    public Optional<Administrator> findById(UUID administratorId) {
        return administrators.stream().filter(admin -> admin.getAdministratorId().equals(administratorId)).findFirst();
    }
    @Override
    public void add(Administrator administrator) {
        administrators.add(administrator);
    }
}
