package message;

import messaging.RegistrationSubmittedHandler_Kafka;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import port.IntegrationEventHandler;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"registration-submitted"}, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class RegistrationSubmittedHandler_KafkaTest {}
