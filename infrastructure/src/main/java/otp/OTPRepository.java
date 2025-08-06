package otp;

import java.util.UUID;

public interface OTPRepository {
    void add(OTP otp);
    OTP get(UUID id);
}
