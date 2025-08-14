package authentication;

import authentication.api.IAuthenticationServiceFactory;
import org.junit.Test;

public class mockUserAuthenticationServiceFactoryTest {
    @Test
    public void testCreateAuthenticationService() {
        IAuthenticationServiceFactory authenticationServiceFactory = new MockAuthenticationServiceFactory();
    }
}
