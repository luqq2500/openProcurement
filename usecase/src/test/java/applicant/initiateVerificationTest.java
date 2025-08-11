package applicant;

import event.EventVerificationInitiator;
import event.InitiateEventVerification;
import event.EventVerificationRequest;
import mock.MockCustomOTPRepository;
import mock.MockCustomOTPService;
import mock.MockEmailService;
import notification.NotificationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import token.CustomOTP;
import token.TokenRepository;
import token.TokenService;

public class initiateVerificationTest {
    private EventVerificationRequest mockRequest;
    private TokenRepository<CustomOTP> mockOTPRepository;
    private EventVerificationInitiator requestor;
    @Before
    public void setUp(){
        mockRequest = new EventVerificationRequest("hakimluqq25@gmail.com");
        NotificationService notificationService = new MockEmailService();
        mockOTPRepository = new MockCustomOTPRepository();
        TokenService<CustomOTP> tokenService = new MockCustomOTPService(mockOTPRepository);
        requestor = new InitiateEventVerification(notificationService, tokenService);
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
        requestor.initiate(new EventVerificationRequest("random"));
    }

    @Test
    public void twoDifferentRequestFrom_bothRequestsShouldBeEnabled(){
        requestor.initiate(mockRequest);
        requestor.initiate(new EventVerificationRequest("random"));
        Assert.assertTrue(mockOTPRepository.tokens().getFirst().isEnabled());
        Assert.assertTrue(mockOTPRepository.tokens().getLast().isEnabled());
    }
}
