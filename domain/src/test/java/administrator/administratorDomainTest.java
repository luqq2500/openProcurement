package administrator;

import administrator.exception.AdministratorRoleInvalidException;
import administrator.exception.AdministratorRoleUnchangedException;
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
    public void updateInvalidRole_shouldThrowException() {
        RuntimeException error = Assert.assertThrows(AdministratorRoleUnchangedException.class, () -> {
            administrator.updateRole(AdministratorRoles.PROCUREMENT_ADMINISTRATOR);
        });
        System.out.println(error.getMessage());
    }
    @Test
    public void updateValidRole_shouldNotThrowException() {
        administrator.updateRole(AdministratorRoles.SYSTEM_ADMINISTRATOR);
    }
}
