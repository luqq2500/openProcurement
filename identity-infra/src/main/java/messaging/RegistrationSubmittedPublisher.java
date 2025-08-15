package messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import company.events.integrationEvents.RegistrationSubmitted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import port.IntegrationEventPublisher;

@Component
public class RegistrationSubmittedPublisher implements IntegrationEventPublisher<RegistrationSubmitted> {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationSubmittedPublisher.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private static final String TOPIC = "registration-submitted";

    public RegistrationSubmittedPublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publish(RegistrationSubmitted event) {
        try{
            String eventJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(TOPIC, event.getEventId().toString(), eventJson);
            logger.info("Published event - Type: {}, Id: {}", event.getEventName(), event.getEventId());
        } catch (Exception e){
            logger.error("Error publishing event - Type: {}, Id: {}", event.getEventName(), event.getEventId());
            throw new RuntimeException("Event publishing failed", e);
        }
    }
}
