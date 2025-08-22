package message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import identities.address.Address;
import identities.address.Country;
import identities.company.Structure;
import identities.registration.AccountAdministratorDetails;
import identities.registration.CompanyDetails;
import identities.registration.RegistrationApplication;
import identities.registration.RegistrationStatus;
import identities.registration.events.RegistrationSubmitted;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.kafka.core.KafkaTemplate;
import port.IntegrationEventPublisher;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class RegistrationSubmittedPublisherTest {
    @Test
    public void test() {
        KafkaTemplate<String, String> kafkaTemplate = mock(KafkaTemplate.class);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        IntegrationEventPublisher<RegistrationSubmitted> publisher = new RegistrationSubmitted_KafkaPublisher(
                kafkaTemplate, objectMapper
        );
        UUID requestId = UUID.randomUUID();
        RegistrationApplication registrationApplication = new RegistrationApplication(requestId,
                new CompanyDetails(",", new Address("1", "1", "1",
                        "1", "43900", "sel", Country.MALAYSIA), "2222", Structure.SOLE
                ),
                new AccountAdministratorDetails("1", "1", "username", "123"),
                RegistrationStatus.UNDER_REVIEW, LocalDateTime.now(), 1, null);

        RegistrationSubmitted event = new RegistrationSubmitted(registrationApplication);

        publisher.publish(event);

        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        verify(kafkaTemplate, times(1))
                .send(eq(event.getEventType()), keyCaptor.capture(), messageCaptor.capture());

        System.out.println("Key: " + keyCaptor.getValue());
        System.out.println("Message: " + messageCaptor.getValue());
    }
}
