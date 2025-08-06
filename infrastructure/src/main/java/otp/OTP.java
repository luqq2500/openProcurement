package otp;

import java.time.LocalDateTime;
import java.util.UUID;

public class OTP {
    private final UUID id;
    private final String password;
    private final LocalDateTime expiration;

    public OTP(String password, LocalDateTime expiration) {
        this.id = UUID.randomUUID();
        this.password = password;
        this.expiration = expiration;
    }
    public void validatePassword(String password) {
        validateExpiry();
        if (!this.password.equals(password)) {
            throw new InvalidOTP("One time password is incorrect.");
        }
        validateExpiry();
    }
    private void validateExpiry() {
        if (this.expiration.isAfter(LocalDateTime.now())) {
            throw new InvalidOTP("Expired one time password.");
        }
    }
    public UUID getId() {return id;}
    public String getPassword() {return password;}
}
