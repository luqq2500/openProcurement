package applicant;

import mock.MockOTPRepository;
import mock.MockOTPService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import otp.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class mockOTPServiceTest {
    private OTPService otpService;
    private OTPRepository otpRepository;
    private String mockRequestFrom;
    @Before
    public void setUp() throws Exception {
        mockRequestFrom = "hakimluqq25@gmail.com";
        otpRepository = new MockOTPRepository();
        otpService = new MockOTPService(otpRepository);
    }

    @Test
    public void requestOTP_shouldReturnOTP() {
        OTP otp = otpService.requestFor(mockRequestFrom);
        Assert.assertNotNull(otp);
        Assert.assertEquals(otpRepository.passwords().getFirst().getId(), otp.getId());
    }

    @Test
    public void expiredOTP_shouldThrowException(){
        OTP otp = new OTP(mockRequestFrom, "001122", LocalDateTime.now().plusMinutes(-10));
        otpRepository.add(otp);
        InvalidOTP exception = Assert.assertThrows(InvalidOTP.class, ()-> otpService.verify(otp.getId(), otp.getPassword()));
        System.out.println(exception.getMessage());
    }

    @Test
    public void wrongOTP_shouldThrowException(){
        OTP otp = new OTP(mockRequestFrom, "001122", LocalDateTime.now().plusMinutes(10));
        otpRepository.add(otp);
        InvalidOTP exception = Assert.assertThrows(InvalidOTP.class, ()-> otpService.verify(otp.getId(), "112233"));
        System.out.println(exception.getMessage());
    }

    @Test
    public void requestOTPTwice_previousOTPShouldBeDisabled(){
        otpService.requestFor(mockRequestFrom);
        otpService.requestFor(mockRequestFrom);
        Assert.assertEquals(otpRepository.passwords().getFirst().getRequestFrom(), otpRepository.passwords().getLast().getRequestFrom());
        Assert.assertTrue(otpRepository.passwords().getLast().isEnabled());
        Assert.assertFalse(otpRepository.passwords().getFirst().isEnabled());
    }

    @Test
    public void wrongOTPId_shouldThrowException(){
        OTP otp = new OTP(mockRequestFrom, "001122", LocalDateTime.now().plusMinutes(10));
        otpRepository.add(otp);
        InvalidOTP exception = Assert.assertThrows(InvalidOTP.class, ()-> otpService.verify(UUID.randomUUID(), otp.getPassword()));
        System.out.println(exception.getMessage());
    }
}
