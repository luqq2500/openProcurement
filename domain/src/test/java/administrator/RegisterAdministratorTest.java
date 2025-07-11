package administrator;

import administrator.api.AdministratorRegistrator;
import administrator.model.RegisterAdministratorCommand;
import administrator.application.RegisterAnAdministrator;
import administrator.spi.AdministratorRepository;
import org.junit.Assert;
import org.junit.Test;

public class RegisterAdministratorTest {
    @Test
    public void registerAdministrator() {
        AdministratorRepository repository = new InMemoryAdministratorRepository();
        AdministratorRegistrator registrator = new RegisterAnAdministrator(repository);

        RegisterAdministratorCommand command = new RegisterAdministratorCommand(
                "email123@gmail.com",
                "password123",
                "Luqman",
                "Hairurizal"
        );

        Assert.assertNotNull(registrator.register(command));

    }
}
