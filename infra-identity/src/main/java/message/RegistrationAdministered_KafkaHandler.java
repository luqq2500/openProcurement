package message;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import port.IntegrationEventHandler;

@Service
public class RegistrationAdministered_KafkaHandler implements IntegrationEventHandler {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public RegistrationAdministered_KafkaHandler(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }
    @Override
    public void handle() {

    }
}
