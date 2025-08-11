package applicant;

import verification.InitiateVerification;
import verification.VerificationInitiator;
import verification.InitiateVerificationRequest;
import token.otp.MockCustomOTPRepository;
import token.otp.MockCustomOTPService;
import notification.email.MockEmailService;
import notification.NotificationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import token.CustomOTP;
import token.TokenRepository;
import token.TokenService;

public class initiateVerificationTest {
    private InitiateVerificationRequest mockRequest;
    private TokenRepository<CustomOTP> mockOTPRepository;
    private VerificationInitiator requestor;
    @Before
    public void setUp(){
        mockRequest = new InitiateVerificationRequest("hakimluqq25@gmail.com");
        NotificationService notificationService = new MockEmailService();
        mockOTPRepository = new MockCustomOTPRepository();
        TokenService<CustomOTP> tokenService = new MockCustomOTPService(mockOTPRepository);
        requestor = new InitiateVerification(notificationService, tokenService);
    }

    @Test
    public void requestOnce_OTPShouldBeEnabled(){
        requestor.initiate(mockRequest);
        Assert.assertTrue(mockOTPRepository.tokens().getFirst().isEnabled());
    }

    @Test
    public void twoSameRequestFrom_shouldNotThrowException(){
        requestor.initiate(mockRequest);
        requestor.initiate(mockRequest);
    }

    @Test
    public void twoDifferentRequestFrom_shouldNotThrowException(){
        requestor.initiate(mockRequest);
        requestor.initiate(new InitiateVerificationRequest("random"));
    }

    @Test
    public void twoDifferentRequestFrom_bothRequestsShouldBeEnabled(){
        requestor.initiate(mockRequest);
        requestor.initiate(new InitiateVerificationRequest("random"));
        Assert.assertTrue(mockOTPRepository.tokens().getFirst().isEnabled());
        Assert.assertTrue(mockOTPRepository.tokens().getLast().isEnabled());
    }
}
