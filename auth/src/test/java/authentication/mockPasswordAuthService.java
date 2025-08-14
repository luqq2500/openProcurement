package authentication;

import authentication.api.UserAuthenticationService;
import user.User;
import user.event.UserAuthenticationFailed;
import user.event.UserAuthenticationPassed;
import user.spi.UserRepository;

public class mockPasswordAuthService implements UserAuthenticationService<PasswordCredential> {
    private final UserRepository userRepository;
    private final IEventBus eventBus;
    public mockPasswordAuthService(UserRepository userRepository, IEventBus eventBus) {
        this.userRepository = userRepository;
        this.eventBus = eventBus;
    }
    @Override
    public void authenticate(PasswordCredential credentials) {
        User user = userRepository.getByEmail(credentials.getEmail());
        if (user.validatePassword(credentials.getPassword())){
            eventBus.publish(new UserAuthenticationPassed(user.getId()));
        }else{
            eventBus.publish(new UserAuthenticationFailed());
        }
    }
}
