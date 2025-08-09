package mock;

import ddd.DomainService;
import otp.InvalidOTP;
import otp.OTP;
import otp.OTPRepository;
import otp.OTPService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@DomainService
public class MockOTPService implements OTPService {
    private final OTPRepository repository;

    public MockOTPService(OTPRepository repository) {
        this.repository = repository;
    }

    @Override
    public OTP requestFor(String from, LocalDateTime expiryTime) {
        disablePreviousRequest(from);
        Random random = new Random();
        int range = 100000;
        String password = String.format("%0" + String.valueOf(range).length() + "d", random.nextInt(range));
        OTP otp = new OTP(from, password, expiryTime);
        repository.add(otp);
        return otp;
    }

    private void disablePreviousRequest(String from) {
        Optional<OTP> otpRequested = repository.findByRequestFrom(from);
        if (otpRequested.isPresent()) {
            otpRequested.get().disable();
            repository.update(otpRequested.get());
        }
    }

    @Override
    public OTP verify(UUID id, String password) {
        OTP otp = repository.get(id);
        if (!otp.isEnabled()){
            throw new InvalidOTP("OTP has been disabled.");
        }
        otp.validateExpiryTime();
        otp.validatePassword(password);
        return otp;
    }
}
