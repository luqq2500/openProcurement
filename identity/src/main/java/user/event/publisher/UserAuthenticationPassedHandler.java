package user.event.publisher;

import event.DomainEventHandler;
import user.event.UserAuthenticationPassed;

public class UserAuthenticationPassedHandler implements DomainEventHandler<UserAuthenticationPassed> {
    @Override
    public void handle(UserAuthenticationPassed event) {}
}
