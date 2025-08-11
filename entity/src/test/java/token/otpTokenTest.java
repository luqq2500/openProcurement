package token;

import org.junit.Assert;
import org.junit.Test;
import otp.InvalidOTP;

import java.time.LocalDateTime;

public class otpTokenTest {
    @Test
    public void otpValid() {
        Token token = new CustomOTP("hakimluqq25@gmail.com", "010101", LocalDateTime.now().plusMinutes(1));
        Assert.assertTrue(token.enabled);
    }
    @Test
    public void expiredOtp() {
        Token token = new CustomOTP("hakimluqq25@gmail.com", "010101", LocalDateTime.now().plusMinutes(-15));
        InvalidOTP exception = Assert.assertThrows(InvalidOTP.class, token::validateExpiry);
        System.out.println(exception.getMessage());
    }
    @Test
    public void wrongOtp() {
        Token token = new CustomOTP("hakimluqq25@gmail.com","010101", LocalDateTime.now().plusMinutes(1));
        InvalidOTP exception = Assert.assertThrows(InvalidOTP.class, ()-> token.verify("999999"));
        System.out.println(exception.getMessage());
    }
}
