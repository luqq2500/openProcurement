package token.otp;

import ddd.DomainService;
import token.CustomOTP;
import token.InvalidOTP;
import token.TokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@DomainService
public class MockCustomOTPService implements OTPService {
    private final TokenRepository<CustomOTP> repository;

    public MockCustomOTPService(TokenRepository<CustomOTP> repository) {
        this.repository = repository;
    }


    @Override
    public CustomOTP request(String destination, LocalDateTime expiry) {
        disablePreviousRequest(destination);
        Random random = new Random();
        int range = 100000;
        String password = String.format("%0" + String.valueOf(range).length() + "d", random.nextInt(range));
        CustomOTP otp = new CustomOTP(destination, password, expiry);
        repository.add(otp);
        return otp;
    }

    private void disablePreviousRequest(String destination) {
        Optional<CustomOTP> otp = repository.findByRequestFrom(destination);
        if (otp.isPresent()) {
            otp.get().disable();
            repository.update(otp.get());
        }
    }

    @Override
    public CustomOTP verify(UUID tokenId, String token) {
        CustomOTP otp = repository.get(tokenId);
        if (!otp.isEnabled()){
            throw new InvalidOTP("OTP has been disabled.");
        }
        otp.validateExpiry();
        otp.verify(token);
        return otp;
    }
}
