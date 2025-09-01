package messaging;


import org.junit.Test;
import org.springframework.kafka.core.KafkaTemplate;
import port.IntegrationEventPublisher;
import usecase.registration.events.integration.RegistrationSubmitted_IE;

public class RegistrationSubmittedIET {
    @Test
    public void test() {
        KafkaTemplate<String, String> kafkaTemplate;
        IntegrationEventPublisher<RegistrationSubmitted_IE> registrationSubmittedHandler = null;
    }
}
