package otp;

import java.time.LocalDateTime;
import java.util.UUID;

public interface OTPService {
    OTP requestFor(String from, LocalDateTime expiryTime);
    OTP verify(UUID id, String password);
}
