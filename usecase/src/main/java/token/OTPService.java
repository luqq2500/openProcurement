package token;

import java.util.UUID;

public interface OTPService {
    OTP generate();
    void verify(UUID id, String password);
}
