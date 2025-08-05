package otp;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public interface OTPService {
    String generate(int range, LocalDateTime expiration);
    String validate(UUID id, String password);
}
