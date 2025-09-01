package messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import messaging.properties.MessagingProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import port.IntegrationEventPublisher;
import usecase.registration.events.integration.RegistrationSubmitted_IE;

@Component
public class RegistrationSubmittedPublisher_Kafka implements IntegrationEventPublisher<RegistrationSubmitted_IE> {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MessagingProperties properties;
    private final ObjectMapper objectMapper;

    public RegistrationSubmittedPublisher_Kafka(KafkaTemplate<String, String> kafkaTemplate, MessagingProperties properties, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.properties = properties;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publish(RegistrationSubmitted_IE event) {
        String topic = properties.getTopics().getRegistrationSubmitted();
        try{
            String payload = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(topic, payload);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
