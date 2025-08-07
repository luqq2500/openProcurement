package otp;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OTPRepository {
    void add(OTP otp);
    OTP get(UUID id);
    Optional<OTP> findByRequestFrom(String username);
    void update(OTP otp);
    List<OTP> passwords();
}
