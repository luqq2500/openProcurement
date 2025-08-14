package authentication.usecase;

import authentication.api.UserAuthenticator;
import authentication.api.IAuthenticationServiceFactory;
import authentication.credential.AuthenticationCredential;

public class AuthenticateUser implements UserAuthenticator {
    private final IAuthenticationServiceFactory IAuthenticationServiceFactory;

    public AuthenticateUser(IAuthenticationServiceFactory iAuthenticationServiceFactory) {
        IAuthenticationServiceFactory = iAuthenticationServiceFactory;
    }

    @Override
    public void authenticate(AuthenticationCredential authenticationCredential) {

    }
}
