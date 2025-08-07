package applicant;

import applicant.api.CompanyRegistrationRequestVerifier;
import company.CompanyRegistrationRequest;
import company.MockCompanyRegistrationRequestRepository;
import company.spi.CompanyRegistrationRequestRepository;
import email.EmailService;
import email.MockEmailService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import otp.*;

import java.time.LocalDateTime;

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
    public void notExpiredVerification_shouldNotThrowException() throws Exception {
        CompanyRegistrationRequest mockRequest = new CompanyRegistrationRequest(mockRequestFrom);
        OTP mockOTP = new OTP(mockRequestFrom, "001122", LocalDateTime.now().plusMinutes(15));

        mockRequestRepository.add(mockRequest);
        mockOTPRepository.add(mockOTP);
        verifier = new VerifyCompanyRegistrationRequest(mockRequestRepository, mockOTPService, mockEmailService);
        verifier.verify(mockRequest.getId(), mockOTP.getId(), mockOTP.getPassword());
    }

    @Test
    public void expiredVerification_shouldThrowException() throws Exception {
        CompanyRegistrationRequest mockRequest = new CompanyRegistrationRequest(mockRequestFrom);
        OTP mockOTP = new OTP(mockRequestFrom, "001122", LocalDateTime.now().plusMinutes(-15));

        mockRequestRepository.add(mockRequest);
        mockOTPRepository.add(mockOTP);
        verifier = new VerifyCompanyRegistrationRequest(mockRequestRepository, mockOTPService, mockEmailService);
        InvalidOTP exception = Assert.assertThrows(InvalidOTP.class, () -> verifier.verify(mockRequest.getId(), mockOTP.getId(), mockOTP.getPassword()));
        System.out.println(exception.getMessage());
    }

    @Test
    public void wrongPassword_shouldThrowException() throws Exception {
        CompanyRegistrationRequest mockRequest = new CompanyRegistrationRequest(mockRequestFrom);
        OTP mockOTP = new OTP(mockRequestFrom,"001122", LocalDateTime.now().plusMinutes(15));

        mockRequestRepository.add(mockRequest);
        mockOTPRepository.add(mockOTP);
        verifier = new VerifyCompanyRegistrationRequest(mockRequestRepository, mockOTPService, mockEmailService);
        InvalidOTP exception = Assert.assertThrows(
                InvalidOTP.class,
                () -> verifier.verify(mockRequest.getId(), mockOTP.getId(), "224466"));
        System.out.println(exception.getMessage());
    }

    @Test
    public void disabledOTP_shouldThrowException(){
        CompanyRegistrationRequest mockRequest = new CompanyRegistrationRequest(mockRequestFrom);
        OTP mockOTP = new OTP(mockRequestFrom,"001122", LocalDateTime.now().plusMinutes(15));
        mockOTP.disable();

        mockRequestRepository.add(mockRequest);
        mockOTPRepository.add(mockOTP);
        verifier = new VerifyCompanyRegistrationRequest(mockRequestRepository, mockOTPService, mockEmailService);
        InvalidOTP exception = Assert.assertThrows(InvalidOTP.class,
                () -> verifier.verify(mockRequest.getId(), mockOTP.getId(), "001122"));
        System.out.println(exception.getMessage());
    }
}
