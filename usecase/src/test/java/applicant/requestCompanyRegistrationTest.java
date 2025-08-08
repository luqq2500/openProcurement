package applicant;

import applicant.api.CompanyRegistrationRequestor;
import company.MockCompanyRegistrationRequestRepository;
import company.spi.CompanyRegistrationRequestRepository;
import email.EmailService;
import email.MockEmailService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import otp.*;

public class requestCompanyRegistrationTest {
    private String mockEmail;
    private OTPRepository mockOTPRepository;
    private CompanyRegistrationRequestor requestor;
    @Before
    public void setUp(){
        mockEmail = "hakimluqq25@gmail.com";
        CompanyRegistrationRequestRepository repository = new MockCompanyRegistrationRequestRepository();
        mockOTPRepository = new MockOTPRepository();
        EmailService emailService = new MockEmailService();
        OTPService OTPService = new MockOTPService(mockOTPRepository);
        requestor = new RequestCompanyRegistration(repository, emailService, OTPService);
    }

    @Test
    public void requestOnce_OTPShouldBeEnabled(){
        requestor.request(mockEmail);
        Assert.assertTrue(mockOTPRepository.passwords().getFirst().isEnabled());
    }

    @Test
    public void twoSameRequestFrom_shouldNotThrowException(){
        requestor.request(mockEmail);
        requestor.request(mockEmail);
    }

    @Test
    public void twoDifferentRequestFrom_shouldNotThrowException(){
        requestor.request(mockEmail);
        requestor.request("random@email.com");
    }

    @Test
    public void twoDifferentRequestFrom_bothRequestsShouldBeEnabled(){
        requestor.request(mockEmail);
        requestor.request("random@email.com");
        Assert.assertTrue(mockOTPRepository.passwords().getFirst().isEnabled());
        Assert.assertTrue(mockOTPRepository.passwords().getLast().isEnabled());
    }
}
