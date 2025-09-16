package event;

import usecase.event.InMemoryEventBus;
import usecase.invitation.events.UserInvited;
import usecase.invitation.events.UserInvitedDomainHandler;
import usecase.registration.events.RegistrationRequested;
import usecase.registration.events.RegistrationRequestedHandler;
import usecase.registration.events.RegistrationSubmitted;
import usecase.registration.events.RegistrationSubmittedHandler;

public class DomainEventConfiguration {
    public static void registerAllDomainEvents() {
        InMemoryEventBus.INSTANCE.clearSubscribers();
        DomainEventHandler<RegistrationRequested> registrationRequestedHandler = new RegistrationRequestedHandler();
        DomainEventHandler<RegistrationSubmitted> registrationSubmittedHandler = new RegistrationSubmittedHandler(null);
        DomainEventHandler<UserInvited> userInvitedDomainEventHandler = new UserInvitedDomainHandler(null);
        InMemoryEventBus.INSTANCE.subscribe(registrationRequestedHandler);
        InMemoryEventBus.INSTANCE.subscribe(registrationSubmittedHandler);
        InMemoryEventBus.INSTANCE.subscribe(userInvitedDomainEventHandler);
    }
}
