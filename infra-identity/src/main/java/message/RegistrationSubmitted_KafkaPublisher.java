package message;

import com.fasterxml.jackson.databind.ObjectMapper;
import registration.events.RegistrationSubmitted;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import port.IntegrationEventPublisher;

@Service
public class RegistrationSubmitted_KafkaPublisher implements IntegrationEventPublisher<RegistrationSubmitted> {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    public RegistrationSubmitted_KafkaPublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }
    @Override
    public void publish(RegistrationSubmitted event) {
        try{
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(event.getEventType(), event.getEventId().toString(), message);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
