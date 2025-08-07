package token;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class MockOTPService implements OTPService {
    private final OTPRepository repository;
    private final LocalDateTime mockExpiryTime = LocalDateTime.now().plusMinutes(15);

    public MockOTPService(OTPRepository repository) {
        this.repository = repository;
    }

    @Override
    public OTP generate() {
        Random random = new Random();
        int range = 100000;
        String password = String.format("%0" + String.valueOf(range).length() + "d", random.nextInt(range));
        OTP otp = new OTP(password, mockExpiryTime);
        repository.add(otp);
        return otp;
    }

    @Override
    public void verify(UUID id, String password) {
        OTP otp = repository.get(id);
        otp.validateExpiryTime();
        otp.validatePassword(password);
    }
}
