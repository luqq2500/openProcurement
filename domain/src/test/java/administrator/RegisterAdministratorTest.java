package administrator;
import administrator.api.AdministratorRegistrator;
import administrator.exception.AdministratorAlreadyExistException;
import administrator.model.Administrator;
import administrator.application.command.RegisterAdministratorCommand;
import administrator.application.RegisterAdministrator;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RegisterAdministratorTest {
    @Test
    public void registerNewAdministratorShouldReturnAdministrator() {
        var administrators = List.of(
                new Administrator("email123@gmail.com", "password123", "Luqman", "Hairurizal", "Application")
        );
        AdministratorRegistrator registrator = new RegisterAdministrator(()->administrators);
        RegisterAdministratorCommand command = new RegisterAdministratorCommand(
                "email456@gmail.com",
                "password123",
                "Luqman",
                "Hairurizal",
                "Application Approval"
        );
        Assert.assertNotNull(registrator.register(command));
    }

    @Test
    public void registerDuplicateAdministratorShouldThrowException() {
        var administrators = List.of(
                new Administrator("email123@gmail.com", "password123", "Luqman", "Hairurizal", "Registration")
        );
        AdministratorRegistrator registrator = new RegisterAdministrator(()->administrators);
        RegisterAdministratorCommand command = new RegisterAdministratorCommand(
                "email123@gmail.com",
                "password123",
                "Luqman",
                "Hairurizal",
                "Registration"
        );
        Assert.assertThrows(AdministratorAlreadyExistException.class, ()->{registrator.register(command);});
    }
}
