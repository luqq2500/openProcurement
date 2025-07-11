package administrator;
import administrator.api.AdministratorRegistrator;
import administrator.exception.AdministratorAlreadyExistException;
import administrator.model.Administrator;
import administrator.model.RegisterAdministratorCommand;
import administrator.application.RegisterAnAdministrator;
import org.junit.Assert;
import org.junit.Test;
import java.util.stream.Stream;

public class RegisterAdministratorTest {
    @Test
    public void registerNewAdministratorShouldReturnAdministrator() {
        var administrators = Stream.of(
                new Administrator("email123@gmail.com", "password123", "Luqman", "Hairurizal", "Application")
        );
        AdministratorRegistrator registrator = new RegisterAnAdministrator(()->administrators);
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
        var administrators = Stream.of(
                new Administrator("email123@gmail.com", "password123", "Luqman", "Hairurizal", "Registration")
        );
        AdministratorRegistrator registrator = new RegisterAnAdministrator(()->administrators);
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
