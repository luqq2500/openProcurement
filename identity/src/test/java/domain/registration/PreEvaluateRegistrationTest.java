package domain.registration;

import domain.company.spi.CompanyRepository;
import domain.employee.spi.EmployeeRepository;
import domain.registration.api.RegistrationPreEvaluator;
import domain.registration.spi.RegistrationRepository;
import domain.registration.usecase.PreEvaluateRegistration;
import event.DomainEvent;
import event.EventBus;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PreEvaluateRegistrationTest {
    @Mock
    private RegistrationRepository registrationRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EventBus eventBus;

    @Test
    public void testPreEvaluateRegistration() {
        MockitoAnnotations.initMocks(this);
        when(registrationRepository.findByCompanyName(any(String.class))).thenReturn(Optional.empty());
        when(registrationRepository.findByBrn(any(String.class))).thenReturn(Optional.empty());
        when(eventBus.getInstance()).thenReturn(eventBus);
        RegistrationPreEvaluator registrationPreEvaluator = new PreEvaluateRegistration(employeeRepository, registrationRepository, eventBus);
        registrationPreEvaluator.evaluate(UUID.randomUUID(), "tera", "2020", "hakimluqq25@gmail.com");
    }
}
