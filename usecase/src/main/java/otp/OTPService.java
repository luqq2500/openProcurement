package otp;

import java.util.UUID;

public interface OTPService {
    OTP requestFor(String from);
    void verify(UUID id, String password);
}
