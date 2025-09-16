package usecase.registration.events;

import event.DomainEventHandler;

public class RegistrationRequestedHandler implements DomainEventHandler<RegistrationRequested> {
    @Override
    public void handle(RegistrationRequested event) {
        System.out.println(getEvent().toString() + " is handling " + event.toString());
    }

    @Override
    public Class<RegistrationRequested> getEvent() {
        return RegistrationRequested.class;
    }
}
