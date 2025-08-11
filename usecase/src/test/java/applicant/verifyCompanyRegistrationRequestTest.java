package applicant;

import applicant.api.CompanyRegistrationRequestVerifier;
import event.VerifyEventVerificationRequest;
import mock.*;
import company.spi.CompanyRegistrationRequestRepository;
import notification.NotificationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import token.CustomOTP;
import token.TokenRepository;
import token.TokenService;

import java.time.LocalDateTime;
import java.util.UUID;

public class verifyCompanyRegistrationRequestTest {
    private CompanyRegistrationRequestRepository mockRequestRepository;
    private NotificationService mockEmailService;
    private TokenRepository<CustomOTP> mockTokenRepository;
    private TokenService<CustomOTP> mockTokenService;
    private String mockRequestFrom;
    private CompanyRegistrationRequestVerifier verifier;

    @Before
    public void setUp() throws Exception {
        mockRequestFrom = "hakimluqq25@gmail.com";
        mockRequestRepository = new MockCompanyRegistrationRequestRepository();
        mockTokenRepository = new MockCustomOTPRepository();
        mockTokenService = new MockCustomOTPService(mockTokenRepository);
        mockEmailService = new MockEmailService();
    }

    @Test
    public void correctToken_shouldNotThrowException() throws Exception {
        CustomOTP otp = new CustomOTP(mockRequestFrom, "001122", LocalDateTime.now().plusMinutes(15));
        mockTokenRepository.add(otp);
        verifier = new VerifyCompanyRegistrationRequest(mockRequestRepository, mockTokenService, mockEmailService);
        VerifyEventVerificationRequest verification = new VerifyEventVerificationRequest(otp.getId(), otp.getToken());
        verifier.verify(verification);
    }

    @Test
    public void disabledToken_shouldThrowException() {
        CustomOTP mockToken = new CustomOTP(mockRequestFrom, "001122", LocalDateTime.now().plusMinutes(15));
        mockToken.disable();
        mockTokenRepository.add(mockToken);
        verifier = new VerifyCompanyRegistrationRequest(mockRequestRepository, mockTokenService, mockEmailService);
        VerifyEventVerificationRequest verification = new VerifyEventVerificationRequest(mockToken.getId(), mockToken.getToken());
        InvalidOTP exception = Assert.assertThrows(InvalidOTP.class, ()-> verifier.verify(verification));
        System.out.println(exception.getMessage());
    }

    @Test
    public void wrongToken_shouldThrowException() {
        CustomOTP mockOTP = new CustomOTP(mockRequestFrom, "001122", LocalDateTime.now().plusMinutes(15));
        mockTokenRepository.add(mockOTP);
        verifier = new VerifyCompanyRegistrationRequest(mockRequestRepository, mockTokenService, mockEmailService);
        VerifyEventVerificationRequest verification = new VerifyEventVerificationRequest(mockOTP.getId(), "wrongtoken");
        InvalidOTP exception = Assert.assertThrows(InvalidOTP.class, ()-> verifier.verify(verification));
        System.out.println(exception.getMessage());
    }

    @Test
    public void tokenIdDoesNotExist_shouldThrowException() {
        CustomOTP mockOTP = new CustomOTP(mockRequestFrom, "001122", LocalDateTime.now().plusMinutes(15));
        mockTokenRepository.add(mockOTP);
        verifier = new VerifyCompanyRegistrationRequest(mockRequestRepository, mockTokenService, mockEmailService);
        VerifyEventVerificationRequest verification = new VerifyEventVerificationRequest(UUID.randomUUID(), mockOTP.getToken());
        InvalidOTP exception = Assert.assertThrows(InvalidOTP.class, ()-> verifier.verify(verification));
        System.out.println(exception.getMessage());
    }

    @Test
    public void expiredToken_shouldThrowException() throws Exception {
        CustomOTP mockOTP = new CustomOTP(mockRequestFrom, "001122", LocalDateTime.now().plusMinutes(-15));
        mockTokenRepository.add(mockOTP);
        verifier = new VerifyCompanyRegistrationRequest(mockRequestRepository, mockTokenService, mockEmailService);
        VerifyEventVerificationRequest verification = new VerifyEventVerificationRequest(mockOTP.getId(), mockOTP.getToken());
        InvalidOTP exception = Assert.assertThrows(InvalidOTP.class, () -> verifier.verify(verification));
        System.out.println(exception.getMessage());
    }

    @Test
    public void twoSameRequests_butVerifyOnlyEnabledToken_shouldNotThrowException(){
        CustomOTP otp1 = mockTokenService.request(mockRequestFrom, LocalDateTime.now().plusMinutes(15));
        CustomOTP otp2 = mockTokenService.request(mockRequestFrom, LocalDateTime.now().plusMinutes(15));
        verifier = new VerifyCompanyRegistrationRequest(mockRequestRepository, mockTokenService, mockEmailService);
        VerifyEventVerificationRequest verification = new VerifyEventVerificationRequest(otp2.getId(), otp2.getToken());
        verifier.verify(verification);
    }

    @Test
    public void twoSameRequests_butVerifyOnlyDisabledToken_shouldThrowException(){
        CustomOTP otp1 = mockTokenService.request(mockRequestFrom, LocalDateTime.now().plusMinutes(15));
        CustomOTP otp2 = mockTokenService.request(mockRequestFrom, LocalDateTime.now().plusMinutes(15));
        verifier = new VerifyCompanyRegistrationRequest(mockRequestRepository, mockTokenService, mockEmailService);
        VerifyEventVerificationRequest verification = new VerifyEventVerificationRequest(otp1.getId(), otp1.getToken());
        InvalidOTP exception = Assert.assertThrows(InvalidOTP.class, () -> verifier.verify(verification));
        System.out.println(exception.getMessage());
    }

    @Test
    public void verifySameTokenTwice_secondVerifyShouldThrowException(){
        CustomOTP otp = mockTokenService.request(mockRequestFrom, LocalDateTime.now().plusMinutes(15));
        verifier = new VerifyCompanyRegistrationRequest(mockRequestRepository, mockTokenService, mockEmailService);
        VerifyEventVerificationRequest verification = new VerifyEventVerificationRequest(otp.getId(), otp.getToken());
        verifier.verify(verification);
        InvalidOTP exception = Assert.assertThrows(InvalidOTP.class, () -> verifier.verify(verification));
        System.out.println(exception.getMessage());
    }

    @Test
    public void verifiedToken_shouldBeDisabled(){
        CustomOTP otp = mockTokenService.request(mockRequestFrom, LocalDateTime.now().plusMinutes(15));
        verifier = new VerifyCompanyRegistrationRequest(mockRequestRepository, mockTokenService, mockEmailService);
        VerifyEventVerificationRequest verification = new VerifyEventVerificationRequest(otp.getId(), otp.getToken());
        verifier.verify(verification);
        Assert.assertFalse(otp.isEnabled());
    }
}
