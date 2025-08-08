package otp;

import java.time.LocalDateTime;
import java.util.UUID;

public class OTP {
    private final UUID id;
    private final String requestFrom;
    private final String password;
    private final LocalDateTime expiration;
    private Boolean enabled;

    public OTP(String requestFrom, String password, LocalDateTime expiration) {
        this.id = UUID.randomUUID();
        this.requestFrom = requestFrom;
        this.password = password;
        this.expiration = expiration;
        this.enabled = true;
    }
    public void validatePassword(String password) {
        validateExpiryTime();
        if (!this.password.equals(password)) {
            throw new InvalidOTP("One time password is incorrect.");
        }
        this.enabled = false;
    }
    public void validateExpiryTime() {
        if (!this.expiration.isAfter(LocalDateTime.now())) {
            throw new InvalidOTP("Expired one time password.");
        }
    }
    public void disable(){
        this.enabled = false;
    }
    public UUID getId() {return id;}
    public String getRequestFrom() {return requestFrom;}
    public String getPassword() {return password;}
    public LocalDateTime getExpiration() {return expiration;}
    public boolean isEnabled() {return enabled;}
}
