package registration.event;

import identities.registration.events.RegistrationSubmitted;
import port.IntegrationEventHandler;
import registration.Registration;
import registration.spi.RegistrationRepository;

public class MockRegistrationSubmittedHandler implements IntegrationEventHandler<RegistrationSubmitted> {
    RegistrationRepository registrationRepository;
    @Override
    public void handle(RegistrationSubmitted event) {
        Registration registration = new Registration(
                event.getRegistrationId(),
                event.getCompanyDetails().companyName(),
                event.getCompanyDetails().brn(),
                event.getCompanyDetails().structure().toString()
        );
        registrationRepository.add(registration);
    }
}
