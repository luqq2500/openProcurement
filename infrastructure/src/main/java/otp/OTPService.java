package otp;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public interface OTPService {
    OTP generate();
    void validate(UUID id, String password);
}
