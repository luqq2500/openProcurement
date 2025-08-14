package authentication;

import authentication.api.UserAuthenticationService;
import authentication.api.IAuthenticationServiceFactory;
import authentication.credential.CredentialType;

import java.util.ArrayList;
import java.util.List;

public class MockAuthenticationServiceFactory implements IAuthenticationServiceFactory {
    private List<UserAuthenticationService<?>> services = new ArrayList<>();
    @Override
    public void add(UserAuthenticationService<?> service) {
        services.add(service);
    }
    @Override
    public UserAuthenticationService<?> get(CredentialType type) {
        return switch (type){

        }
    }
}
