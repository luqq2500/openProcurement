package user.event.publisher;

import ddd.DomainEventHandler;
import user.event.UserAuthenticationPassed;

public class UserAuthenticationPassedHandler implements DomainEventHandler<UserAuthenticationPassed> {
    @Override
    public void handle(UserAuthenticationPassed event) {}
}
