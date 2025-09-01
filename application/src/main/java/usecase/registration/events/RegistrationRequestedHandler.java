package usecase.registration.events;

public class RegistrationRequestedHandler implements event.DomainEventHandler<RegistrationRequested> {
    @Override
    public void handle(RegistrationRequested event) {}

    @Override
    public Class<RegistrationRequested> getEvent() {
        return RegistrationRequested.class;
    }
}
