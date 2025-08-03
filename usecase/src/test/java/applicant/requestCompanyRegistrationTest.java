package applicant;

import applicant.api.CompanyRegistrationRequestor;
import applicant.dto.RequestCompanyRegistrationCommand;
import company.CompanyRegistrationRequest;
import company.MockCompanyRegistrationRequestRepository;
import company.spi.CompanyRegistrationRequestRepository;
import notification.MockNotificationService;
import notification.NotificationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class requestCompanyRegistrationTest {
    private CompanyRegistrationRequestor requestor;
    @Before
    public void setUp() {
        Applicant mockApplicant = new Applicant("Luqman", "Hakim", "hakim@gmail.com", "60104305988");
        List<CompanyRegistrationRequest> requests = List.of(
                new CompanyRegistrationRequest(mockApplicant, LocalDateTime.now().plusDays(3))
        );
        CompanyRegistrationRequestRepository requestRepository = new MockCompanyRegistrationRequestRepository();
        NotificationService notificationService = new MockNotificationService();
        requests.forEach(requestRepository::add);
        requestor = new RequestCompanyRegistration(requestRepository, notificationService);
    }
    @Test
    public void validEmail_shouldNotThrowException() {
        requestor.request(new RequestCompanyRegistrationCommand(
                "Luqman",
                "Hakim",
                "hakimluqq@gmail.com",
                "0104305988"
        ));
    }
    @Test
    public void invalidEmail_shouldThrowException() {
        RequestCompanyRegistrationCommand invalidEmail = new RequestCompanyRegistrationCommand(
                "Luqman",
                "Hakim",
                "hakimluqq.com",
                "0104305988"
        );
        RuntimeException error = Assert.assertThrows(RuntimeException.class, () -> requestor.request(invalidEmail));
        System.out.println(error.getMessage());
    }
}

