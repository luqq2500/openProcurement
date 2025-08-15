package messaging;

import company.events.RegistrationApplied;
import port.IntegrationEventHandler;

public class RegistrationAppliedIntegrationEventHandler_Kafka implements IntegrationEventHandler<RegistrationApplied> {
    @Override
    public void handle(RegistrationApplied event) {

    }
}
