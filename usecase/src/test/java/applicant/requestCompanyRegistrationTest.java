package applicant;

import applicant.api.CompanyRegistrationRequestor;

import applicant.dto.RequestCompanyRegistrationRequest;
import company.MockCompanyRegistrationRequestRepository;
import company.spi.CompanyRegistrationRequestRepository;
import email.EmailService;
import email.MockEmailService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import otp.MockOTPRepository;
import otp.MockOTPService;
import otp.OTPRepository;
import otp.OTPService;

public class requestCompanyRegistrationTest {
    private OTPRepository mockOTPRepository;
    private CompanyRegistrationRequestor requestor;
    @Before
    public void setUp(){
        CompanyRegistrationRequestRepository repository = new MockCompanyRegistrationRequestRepository();
        mockOTPRepository = new MockOTPRepository();
        EmailService emailService = new MockEmailService();
        OTPService OTPService = new MockOTPService(mockOTPRepository);
        requestor = new RequestCompanyRegistration(repository, emailService, OTPService);
    }

    @Test
    public void requestOnce_shouldNotThrowException(){
        RequestCompanyRegistrationRequest request = new RequestCompanyRegistrationRequest("hakimluqq25@gmail.com");
        requestor.request(request);
    }

    @Test
    public void requestTwice_previousOTPShouldNotValid(){
        RequestCompanyRegistrationRequest request = new RequestCompanyRegistrationRequest("hakimluqq25@gmail.com");
        requestor.request(request);
        requestor.request(request);
        Assert.assertTrue(mockOTPRepository.passwords().getLast().isEnabled());
        Assert.assertFalse(mockOTPRepository.passwords().getFirst().isEnabled());
    }
}
