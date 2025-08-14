package authentication.api;

import authentication.credential.AuthenticationCredential;

public interface UserAuthenticationService {
    <T extends AuthenticationCredential> void authenticate();
}
