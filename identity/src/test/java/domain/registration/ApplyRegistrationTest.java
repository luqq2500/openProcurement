package domain.registration;

import domain.address.Address;
import domain.address.Country;
import domain.company.Structure;
import domain.registration.api.RegistrationApplier;
import domain.registration.events.domain.RegistrationApplicationSubmitted;
import domain.registration.spi.RegistrationRepository;
import domain.registration.spi.RegistrationRequestRepository;
import domain.registration.usecase.ApplyRegistration;
import domain.registration.usecase.dto.RegistrationApplierRequest;
import event.DomainEvent;
import event.EventBus;
import event.InMemoryEventBus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ApplyRegistrationTest {
    private RegistrationApplicationRequest validRequest;
    private RegistrationApplicationRequest invalidRequest;
    private RegistrationApplier applier;
    @Mock
    RegistrationRepository registrationRepository;
    @Mock
    RegistrationRequestRepository registrationRequestRepository;
    @Mock
    EventBus eventBus;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        validRequest = new RegistrationApplicationRequest("hakimluqq25@gmail.com", LocalDateTime.now().plusDays(7));
        invalidRequest = new RegistrationApplicationRequest("hakimluqq25@gmail.com", LocalDateTime.now().plusDays(-7));
        applier = new ApplyRegistration(registrationRequestRepository,registrationRepository,eventBus);
    }

    @Test
    public void testApplyRegistration() throws Exception {
        when(registrationRequestRepository.getById(any(UUID.class))).thenReturn(validRequest);
        when(eventBus.getInstance()).thenReturn(new InMemoryEventBus());
        applier.apply(
                new RegistrationApplierRequest(
                        validRequest.getId(),
                        "texa",
                        new Address("1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA),
                        "dummy",
                        Structure.SOLE,
                        "luqman",
                        "hakim",
                        null,
                        "123"
                )
        );
        verify(registrationRepository).add(any(RegistrationApplication.class));
    }

    @Test
    public void expiredRequest_shouldThrowException() {
        when(registrationRequestRepository.getById(any(UUID.class))).thenReturn(invalidRequest);
        when(eventBus.getInstance()).thenReturn(new InMemoryEventBus());
        applier.apply(
                new RegistrationApplierRequest(
                        validRequest.getId(),
                        "texa",
                        new Address("1", "1", "1", "Sepang",
                                "43900", "Selangor", Country.MALAYSIA),
                        "dummy",
                        Structure.SOLE,
                        "luqman",
                        "hakim",
                        null,
                        "123"
                )
        );
    }
}
