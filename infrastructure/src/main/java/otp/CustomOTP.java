package otp;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class CustomOTP implements OTPService{
    private final OTPRepository repository;
    private final LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(15);

    public CustomOTP(OTPRepository repository) {
        this.repository = repository;
    }

    @Override
    public OTP generate() {
        Random random = new Random();
        int range = 100000;
        String password = String.format("%0" + String.valueOf(range).length() + "d", random.nextInt(range));
        OTP otp = new OTP(password, expiryTime);
        repository.add(otp);
        return otp;
    }

    @Override
    public void validate(UUID id, String password) {
        OTP otp = repository.get(id);
        otp.validatePassword(password);
    }
}
