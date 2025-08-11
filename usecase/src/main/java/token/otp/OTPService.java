package token.otp;

import token.CustomOTP;
import token.TokenService;

import java.time.LocalDateTime;
import java.util.UUID;

public interface OTPService extends TokenService<CustomOTP> {
    @Override
    CustomOTP request(String destination, LocalDateTime expiry);
    @Override
    CustomOTP verify(UUID tokenId, String token);
}
