package user.event.publisher;

import domainEvent.DomainEventHandler;
import user.event.UserAuthenticationPassed;

public class UserAuthenticationPassedHandler implements DomainEventHandler<UserAuthenticationPassed> {
    @Override
    public void handle(UserAuthenticationPassed event) {}
}
