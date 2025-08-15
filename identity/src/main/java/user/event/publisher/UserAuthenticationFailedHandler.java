package user.event.publisher;

import domainEvent.DomainEventHandler;
import user.event.UserAuthenticationFailed;

public class UserAuthenticationFailedHandler implements DomainEventHandler<UserAuthenticationFailed> {
    @Override
    public void handle(UserAuthenticationFailed event) {
    }
}
