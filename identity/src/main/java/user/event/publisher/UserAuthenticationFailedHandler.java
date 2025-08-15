package user.event.publisher;

import event.DomainEventHandler;
import user.event.UserAuthenticationFailed;

public class UserAuthenticationFailedHandler implements DomainEventHandler<UserAuthenticationFailed> {
    @Override
    public void handle(UserAuthenticationFailed event) {
    }
}
