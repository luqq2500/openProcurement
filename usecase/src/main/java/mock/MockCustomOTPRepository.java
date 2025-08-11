package mock;

import token.CustomOTP;
import token.CustomOTPRepository;
import token.InvalidOTP;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MockCustomOTPRepository implements CustomOTPRepository {
    List<CustomOTP> otps = new ArrayList<>();
    @Override
    public void add(CustomOTP customOTP) {
        otps.add(customOTP);
    }

    @Override
    public void update(CustomOTP newOtp) {
        CustomOTP oldOtp = get(newOtp.getId());
        otps.remove(oldOtp);
        otps.add(newOtp);
    }

    @Override
    public Optional<CustomOTP> findByRequestFrom(String requestFrom) {
        return otps.stream()
                .filter(otp -> otp.getRequestFrom().equals(requestFrom))
                .findFirst();
    }

    @Override
    public CustomOTP get(UUID id) {
        return otps.stream()
                .filter(otp -> otp.getId().equals(id))
                .findFirst()
                .orElseThrow(()->new InvalidOTP("OTP do not exist."));
    }

    @Override
    public List<CustomOTP> tokens() {
        return otps;
    }
}
