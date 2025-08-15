package company;

import company.address.Country;
import company.api.RegistrationApplier;
import company.events.integrationEvents.RegistrationSubmitted;
import company.exception.InvalidRegistrationSubmission;
import company.spi.CompanyRepository;
import company.spi.RegistrationApplicationRepository;
import company.spi.RegistrationRequestRepository;
import company.spi.mock.MockCompanyRepository;
import company.spi.mock.MockRegistrationApplicationRepository;
import company.spi.mock.MockRegistrationRequestRepository;
import company.usecase.ApplyRegistration;
import company.usecase.ApplyRegistrationRequest;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import port.IntegrationEventPublisher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class submitRegistrationApplicationTest {
    private RegistrationRequestRepository requestRepository;
    private RegistrationApplicationRepository registrationApplicationRepository;
    @Mock
    private IntegrationEventPublisher<RegistrationSubmitted> mockIntegrationEventPublisher;
    @Before
    public void setUp() throws Exception {
        registrationApplicationRepository = new MockRegistrationApplicationRepository();
        requestRepository = new MockRegistrationRequestRepository();
        doNothing().when(mockIntegrationEventPublisher).publish(any(RegistrationSubmitted.class));
    }

    @Test
    public void validRegistrationRequest_shouldNotThrowException() {
        RegistrationRequest registrationRequest = new RegistrationRequest("hakimluqq25", LocalDateTime.now().plusDays(7));
        requestRepository.save(registrationRequest);
        RegistrationApplier applier = new ApplyRegistration(registrationApplicationRepository, requestRepository, mockIntegrationEventPublisher);
        applier.apply(new ApplyRegistrationRequest(registrationRequest.getId(), "Terraform",
                "1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA,
                "20223344", Structure.SOLE, "2223", "luqman", "hakim", null, "3535"));
        verify(mockIntegrationEventPublisher, times(1)).publish(any(RegistrationSubmitted.class));
    }

    @Test
    public void expiredRegistrationRequest_shouldThrowException() {
        RegistrationRequest registrationRequest = new RegistrationRequest("hakimluqq25", LocalDateTime.now().plusDays(-7));
        requestRepository.save(registrationRequest);
        ApplyRegistrationRequest applyRegistrationRequest = new ApplyRegistrationRequest(registrationRequest.getId(), "Terraform",
                "1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA,
                "20223344", Structure.SOLE, "2223", "luqman", "hakim", null, "3535");
        RegistrationApplier applier = new ApplyRegistration(registrationApplicationRepository, requestRepository, mockIntegrationEventPublisher);
        InvalidRegistrationSubmission error = Assert.assertThrows(InvalidRegistrationSubmission.class, ()-> applier.apply(applyRegistrationRequest));
        System.out.println(error.getMessage());
        verify(mockIntegrationEventPublisher, times(0)).publish(any(RegistrationSubmitted.class));
    }
}
