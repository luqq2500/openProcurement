package applicant;

import applicant.api.CompanyRegistrationRequestVerifier;
import company.CompanyRegistrationRequest;
import mock.MockCompanyRegistrationRequestRepository;
import company.exception.CompanyRegistrationRequestNotFound;
import company.spi.CompanyRegistrationRequestRepository;
import email.EmailService;
import mock.MockEmailService;
import mock.MockOTPRepository;
import mock.MockOTPService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import otp.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class verifyCompanyRegistrationRequestTest {
    private CompanyRegistrationRequestRepository mockRequestRepository;
    private EmailService mockEmailService;
    private OTPRepository mockOTPRepository;
    private OTPService mockOTPService;
    private String mockRequestFrom;
    private CompanyRegistrationRequestVerifier verifier;

    @Before
    public void setUp() throws Exception {
        mockRequestFrom = "hakimluqq25@gmail.com";
        mockRequestRepository = new MockCompanyRegistrationRequestRepository();
        mockOTPRepository = new MockOTPRepository();
        mockOTPService = new MockOTPService(mockOTPRepository);
        mockEmailService = new MockEmailService();
    }

    @Test
    public void correctOTP_shouldNotThrowException() throws Exception {
        CompanyRegistrationRequest mockRequest = new CompanyRegistrationRequest(mockRequestFrom);
        OTP mockOTP = new OTP(mockRequestFrom, "001122", LocalDateTime.now().plusMinutes(15));
        mockRequestRepository.add(mockRequest);
        mockOTPRepository.add(mockOTP);
        verifier = new VerifyRequestCompanyRegistration(mockRequestRepository, mockOTPService, mockEmailService);
        verifier.verify(mockRequest.getId(), mockOTP.getId(), mockOTP.getPassword());
    }

    @Test
    public void disabledOTP_shouldThrowException() {
        CompanyRegistrationRequest mockRequest = new CompanyRegistrationRequest(mockRequestFrom);
        OTP mockOTP = new OTP(mockRequestFrom, "001122", LocalDateTime.now().plusMinutes(15));
        mockOTP.disable();
        mockRequestRepository.add(mockRequest);
        mockOTPRepository.add(mockOTP);
        verifier = new VerifyRequestCompanyRegistration(mockRequestRepository, mockOTPService, mockEmailService);
        InvalidOTP exception = Assert.assertThrows(InvalidOTP.class, ()-> verifier.verify(mockRequest.getId(), mockOTP.getId(), mockOTP.getPassword()));
        System.out.println(exception.getMessage());
    }

    @Test
    public void wrongPassword_shouldThrowException() {
        CompanyRegistrationRequest mockRequest = new CompanyRegistrationRequest(mockRequestFrom);
        OTP mockOTP = new OTP(mockRequestFrom, "001122", LocalDateTime.now().plusMinutes(15));
        mockRequestRepository.add(mockRequest);
        mockOTPRepository.add(mockOTP);
        verifier = new VerifyRequestCompanyRegistration(mockRequestRepository, mockOTPService, mockEmailService);
        InvalidOTP exception = Assert.assertThrows(InvalidOTP.class, ()-> verifier.verify(mockRequest.getId(), mockOTP.getId(), "882899"));
        System.out.println(exception.getMessage());
    }

    @Test
    public void otpIdDoesNotExist_shouldThrowException() {
        CompanyRegistrationRequest mockRequest = new CompanyRegistrationRequest(mockRequestFrom);
        OTP mockOTP = new OTP(mockRequestFrom, "001122", LocalDateTime.now().plusMinutes(15));
        mockRequestRepository.add(mockRequest);
        mockOTPRepository.add(mockOTP);
        verifier = new VerifyRequestCompanyRegistration(mockRequestRepository, mockOTPService, mockEmailService);
        InvalidOTP exception = Assert.assertThrows(InvalidOTP.class, ()-> verifier.verify(mockRequest.getId(), UUID.randomUUID(), mockOTP.getPassword()));
        System.out.println(exception.getMessage());
    }

    @Test
    public void expiredOTP_shouldThrowException() throws Exception {
        CompanyRegistrationRequest mockRequest = new CompanyRegistrationRequest(mockRequestFrom);
        OTP mockOTP = new OTP(mockRequestFrom, "001122", LocalDateTime.now().plusMinutes(-15));
        mockRequestRepository.add(mockRequest);
        mockOTPRepository.add(mockOTP);
        verifier = new VerifyRequestCompanyRegistration(mockRequestRepository, mockOTPService, mockEmailService);
        InvalidOTP exception = Assert.assertThrows(InvalidOTP.class, () -> verifier.verify(mockRequest.getId(), mockOTP.getId(), mockOTP.getPassword()));
        System.out.println(exception.getMessage());
    }

    @Test
    public void twoSameRequests_butVerifyOnlyEnabledOTP_shouldNotThrowException(){
        CompanyRegistrationRequest mockRequest = new CompanyRegistrationRequest(mockRequestFrom);
        mockRequestRepository.add(mockRequest);
        OTP otp1 = mockOTPService.requestFor(mockRequestFrom);
        OTP otp2 = mockOTPService.requestFor(mockRequestFrom);
        verifier = new VerifyRequestCompanyRegistration(mockRequestRepository, mockOTPService, mockEmailService);
        verifier.verify(mockRequest.getId(), otp2.getId(), otp2.getPassword());
    }

    @Test
    public void twoSameRequests_butVerifyOnlyDisabledOTP_shouldThrowException(){
        CompanyRegistrationRequest mockRequest = new CompanyRegistrationRequest(mockRequestFrom);
        mockRequestRepository.add(mockRequest);
        OTP otp1 = mockOTPService.requestFor(mockRequestFrom);
        OTP otp2 = mockOTPService.requestFor(mockRequestFrom);
        verifier = new VerifyRequestCompanyRegistration(mockRequestRepository, mockOTPService, mockEmailService);
        InvalidOTP exception = Assert.assertThrows(InvalidOTP.class, () -> verifier.verify(mockRequest.getId(), otp1.getId(), otp1.getPassword()));
        System.out.println(exception.getMessage());
    }

    @Test
    public void verifySameOTPTwice_secondVerifyShouldThrowException(){
        CompanyRegistrationRequest mockRequest = new CompanyRegistrationRequest(mockRequestFrom);
        mockRequestRepository.add(mockRequest);
        OTP otp = mockOTPService.requestFor(mockRequestFrom);
        verifier = new VerifyRequestCompanyRegistration(mockRequestRepository, mockOTPService, mockEmailService);
        verifier.verify(mockRequest.getId(), otp.getId(), otp.getPassword());
        InvalidOTP exception = Assert.assertThrows(InvalidOTP.class, () -> verifier.verify(mockRequest.getId(), otp.getId(), otp.getPassword()));
        System.out.println(exception.getMessage());
    }

    @Test
    public void verifiedOTP_shouldBeDisabled(){
        CompanyRegistrationRequest mockRequest = new CompanyRegistrationRequest(mockRequestFrom);
        mockRequestRepository.add(mockRequest);
        OTP otp = mockOTPService.requestFor(mockRequestFrom);
        verifier = new VerifyRequestCompanyRegistration(mockRequestRepository, mockOTPService, mockEmailService);
        verifier.verify(mockRequest.getId(), otp.getId(), otp.getPassword());
        Assert.assertFalse(otp.isEnabled());
    }

    @Test
    public void registrationRequestDoesNotExist_shouldThrowException() {
        CompanyRegistrationRequest mockRequest = new CompanyRegistrationRequest(mockRequestFrom);
        mockRequestRepository.add(mockRequest);
        OTP otp = mockOTPService.requestFor(mockRequestFrom);
        verifier = new VerifyRequestCompanyRegistration(mockRequestRepository, mockOTPService, mockEmailService);
        CompanyRegistrationRequestNotFound exception = Assert.assertThrows(CompanyRegistrationRequestNotFound.class, () -> verifier.verify(UUID.randomUUID(), otp.getId(), otp.getPassword()));
        System.out.println(exception.getMessage());
    }
}
