package authentication.api;

import authentication.credential.CredentialType;

public interface IAuthenticationServiceFactory {
    void add(UserAuthenticationService service);
    UserAuthenticationService get(CredentialType type);
}
