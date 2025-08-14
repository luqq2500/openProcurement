package authentication.api;

import authentication.credential.AuthenticationCredential;

public interface UserAuthenticator {
    public void authenticate(AuthenticationCredential authenticationCredential);
}
