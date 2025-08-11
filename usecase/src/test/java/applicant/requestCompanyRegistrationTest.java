package applicant;

import applicant.api.CompanyRegistrationVerificationRequestor;
import event.EventVerificationInitiator;
import applicant.dto.RequestCompanyRegistrationResponse;
import event.InitiateEventVerification;
import mock.MockCustomOTPRepository;
import mock.MockEmailService;
import notification.NotificationService;
import org.junit.Test;
import mock.MockCustomOTPService;
import token.TokenRepository;
import token.TokenService;

public class requestCompanyRegistrationTest {
    @Test
    public void test() {
        NotificationService notificationService = new MockEmailService();
        TokenRepository tokenRepository = new MockCustomOTPRepository();
        TokenService tokenService = new MockCustomOTPService(tokenRepository);
        EventVerificationInitiator eventVerificationInitiator = new InitiateEventVerification(notificationService, tokenService);
        CompanyRegistrationVerificationRequestor requestor = new RequestCompanyRegistrationVerification(eventVerificationInitiator);
        RequestCompanyRegistrationResponse response = requestor.requestFrom("hakimluqq25@gmail.com");
        System.out.println(response);
    }
}
