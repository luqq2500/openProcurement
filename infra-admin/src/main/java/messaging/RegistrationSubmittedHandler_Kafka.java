package messaging;

import org.springframework.kafka.annotation.KafkaListener;
import port.IntegrationEventHandler;

public class RegistrationSubmittedHandler_Kafka implements IntegrationEventHandler {
    @Override
    @KafkaListener(topics = "registration-submitted", groupId = "infra-administration")
    public void handle(String message) {
        System.out.println("Received registration submitted event: " + message);
    }
}


