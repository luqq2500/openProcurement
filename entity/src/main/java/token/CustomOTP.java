package token;


import java.time.LocalDateTime;

public class CustomOTP extends Token {
    public CustomOTP(String requestedFrom, String token, LocalDateTime expiry) {
        super(requestedFrom, token, expiry);
    }

    @Override
    public void verify(String otp) {
        validateExpiry();
        if (!token.equals(otp)) {
            throw new InvalidOTP("Invalid OTP.");
        }
        super.enabled = false;
    }

    @Override
    public void validateExpiry() {
        if (LocalDateTime.now().isAfter(expiry)) {
            throw new InvalidOTP("OTP is expired.");
        }
    }

    @Override
    public void disable() {
        super.enabled = false;
    }

}
