package administrator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class administratorDomainTest {
    private Administrator administrator;
    @Before
    public void setUp() throws Exception {
        this.administrator = new Administrator(
                "Luqman",
                "Hakim",
                "hakimluqq25@gmail.com",
                "123",
                AdministratorRoles.PROCUREMENT_ADMINISTRATOR
        );
    }
    @Test
    public void updateInvalidRoleTest() {
        RuntimeException error = Assert.assertThrows(RuntimeException.class, () -> {
            administrator.updateRole(AdministratorRoles.PROCUREMENT_ADMINISTRATOR);
        });
        System.out.println(error.getMessage());
    }

    @Test
    public void updateValidRoleTest() {
        administrator.updateRole(AdministratorRoles.SYSTEM_ADMINISTRATOR);
    }

    @Test
    public void updateInvalidEmailTest() {
        RuntimeException error = Assert.assertThrows(RuntimeException.class, () -> {
            administrator.updateEmail("hakimluqq25@gmail.com");
        });
        System.out.println(error.getMessage());
    }

    @Test
    public void updateValidEmailTest() {
        administrator.updateEmail("hakimluqq58@gmail.com");
    }
}
