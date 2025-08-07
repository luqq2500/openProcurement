package otp;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class CustomOTPService implements OTPService {
    private final OTPRepository repository;
    private final LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(15);

    public CustomOTPService(OTPRepository repository) {
        this.repository = repository;
    }

    @Override
    public String generate() {
        Random random = new Random();
        int range = 100000;
        String password = String.format("%0" + String.valueOf(range).length() + "d", random.nextInt(range));
        repository.add(new OTP(password, expiryTime));
        return password;
    }
    @Override
    public void verify(UUID id, String password) {
        Optional<OTP> otp = repository.get(id);
        if (otp.isPresent()) {
            otp.get().validateExpiryTime();
            otp.get().validatePassword(password);
        }
    }
}
