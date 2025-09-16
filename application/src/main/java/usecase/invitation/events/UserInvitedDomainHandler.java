package usecase.invitation.events;

import event.DomainEventHandler;
import port.IntegrationEventPublisher;

public class UserInvitedDomainHandler implements DomainEventHandler<UserInvited> {
    private final IntegrationEventPublisher<UserInvitedIE> integrationEventHandler;
    public UserInvitedDomainHandler(IntegrationEventPublisher<UserInvitedIE> integrationEventHandler) {
        this.integrationEventHandler = integrationEventHandler;
    }
    @Override
    public void handle(UserInvited event) {
        System.out.println(getEvent().toString() + " is handling " + event.toString());
    }
    @Override
    public Class<UserInvited> getEvent() {
        return UserInvited.class;
    }
}
