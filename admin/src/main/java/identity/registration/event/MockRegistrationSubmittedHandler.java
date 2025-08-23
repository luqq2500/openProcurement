package identity.registration.event;

import port.IntegrationEventHandler;
import identity.registration.spi.RegistrationRepository;

public class MockRegistrationSubmittedHandler implements IntegrationEventHandler {
    RegistrationRepository registrationRepository;
    @Override
    public void handle(){
    }
}
